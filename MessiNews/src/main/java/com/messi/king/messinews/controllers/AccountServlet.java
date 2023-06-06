package com.messi.king.messinews.controllers;

import com.messi.king.messinews.model.bean.Users;
import com.messi.king.messinews.model.dao.UsersDAO;
import com.messi.king.messinews.model.daoimpl.UsersDAOImpl;
import com.messi.king.messinews.utils.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.*;
import java.util.List;
import java.util.concurrent.ExecutionException;


import javax.mail.MessagingException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@WebServlet(name = "AccountServlet", value = "/Account/*")
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 *1024,
        maxFileSize = 50*1024*1024,
        maxRequestSize = 50*1024*1024
)
public class AccountServlet extends HttpServlet {

    UsersDAOImpl usersDAO = new UsersDAOImpl();
    String errorLogin = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = request.getPathInfo();
        switch (url) {
            case "/Login":
                request.setAttribute("googleLogin", GoogleUtils.getAuthURL());
                request.setAttribute("githubLogin", GithubUtils.getAuthURL());
                request.setAttribute("errorMessage", errorLogin);
                ServletUtils.forward("/views/vwAccount/Login.jsp",request,response);
                break;
            case "/GLogin":
                try {
                    googleLogin(request,response);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/GitLogin":
                try {
                    githubLogin(request, response);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/Register":
                request.setAttribute("email","");
                ServletUtils.forward("/views/vwAccount/Register.jsp",request,response);
                break;
            case "/CheckCaptcha":
                checkCaptcha(request,response);
                break;
            case "/Forgot":
                request.setAttribute("errorForgot", "");
                ServletUtils.forward("/views/vwAccount/Forgot.jsp",request,response);
                break;
            case "/Profile":
                Users user = (Users) session.getAttribute("authUser");
                request.setAttribute("user",user);
                ServletUtils.forward("/views/vwAccount/Profile.jsp",request,response);
                break;
            case "/Password":
                request.setAttribute("errorChangePass", "");
                ServletUtils.forward("/views/vwAccount/Password.jsp",request,response);
                break;

            case "/OTP":
                request.setAttribute("errorOTP", "");
                ServletUtils.forward("/views/vwAccount/OTP.jsp",request,response);
                break;
            case "/IsAvailable":
                String username = request.getParameter("username");
                String email = request.getParameter("email");


                Users userByName = usersDAO.findByUsername(username);
                Users userByEmail = usersDAO.findByEmail(email);

                boolean isAvailable = (userByName == null && userByEmail == null);

                PrintWriter outer = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");

                outer.print(isAvailable);
                outer.flush();
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
                break;
        }
    }

    private void checkCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captcha = request.getParameter("captcha");
        PrintWriter outer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        outer.print(CaptchaUtils.checkCaptcha(captcha));
        outer.flush();
    }

    private void githubLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ExecutionException, InterruptedException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        List<String> resData= GithubUtils.getInfo(code);
        if (resData.get(0).equals("200") && resData.get(1).length()!= 0) {
            Users user = usersDAO.findByEmail(resData.get(1));
            if (user!=null){
                session.setAttribute("auth", true);
                session.setAttribute("authUser", user);
                ServletUtils.redirect("/Home", request, response);
            } else {
                request.setAttribute("email", resData.get(1));
                ServletUtils.forward("/views/vwAccount/Register.jsp", request, response);
            }
        } else {
            ServletUtils.forward("/views/403.jsp",request,response);
        }
    }

    private void googleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ExecutionException, InterruptedException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        List<String> resData= GoogleUtils.getInfo(code);
        if (resData.get(0).equals("200") && resData.get(1).length()!= 0) {
            Users user = usersDAO.findByEmail(resData.get(1));
            if (user!=null){
                session.setAttribute("auth", true);
                session.setAttribute("authUser", user);
                ServletUtils.redirect("/Home", request, response);
            } else {
                request.setAttribute("email", resData.get(1));
                ServletUtils.forward("/views/vwAccount/Register.jsp", request, response);
            }
        } else {
            ServletUtils.forward("/views/403.jsp",request,response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getPathInfo();
        switch (url) {
            case "/Login":
                login(request, response);
                break;
            case "/Register":
                registerUser(request, response);
                break;
            case "/Logout":
                logout(request, response);
                break;
            case "/Profile":
                editProfileUser(request,response);
                break;
            case "/Password":
                changePassword(request,response);
                break;
            case "/Forgot":
                try {
                    forgotPassword(request,response);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/OTP":
                checkOTP(request,response);
                break;
            case "/ChangeAvatar":
                changeAvatar(request,response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
                break;
        }
    }

    private void checkOTP(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String otp = request.getParameter("otp");

        if  (LocalDateTime.now().isAfter(LocalDateTime.parse(session.getAttribute("timeForgot").toString()))) {
            request.setAttribute("errorForgot", "Hết thời gian quy định. Vui lòng thử lại sau");
            ServletUtils.redirect("/Account/Forgot", request, response);
        } else {
            Users user = (Users) session.getAttribute("forgotUser");

            BCrypt.Result result = BCrypt.verifyer().verify(otp.toCharArray(), user.getOtp());

            if (result.verified) {
                String rawpwd = request.getParameter("rawpwd");
                String haspwd = BCrypt.withDefaults().hashToString(12, rawpwd.toCharArray());
                usersDAO.changePassword(user.getId(), haspwd);
                ServletUtils.redirect("/Account/Login",request,response);
            } else {
                request.setAttribute("errorOTP", "Mã OTP không đúng, vui lòng thử lại");
                ServletUtils.forward("/views/vwAccount/OTP.jsp",request,response);
            }
        }
    }

    private void forgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException, MessagingException {
        String username = request.getParameter("username");
        Users user = usersDAO.findByUsername(username);
        if (user==null){
            user = usersDAO.findByEmail(username);

        }

        String code = SendMailUtils.sendEmail(user);

        String otpHash = BCrypt.withDefaults().hashToString(12, code.toCharArray());

        usersDAO.updateOTP(user.getId(),otpHash);

        request.getSession().setAttribute("forgotUser", usersDAO.findById(user.getId()));
        request.getSession().setAttribute("timeForgot", LocalDateTime.now().plusMinutes(5));

        ServletUtils.redirect("/Account/OTP", request, response);
    }

    private void changeAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user  = (Users)request.getSession().getAttribute("authUser");
        String targetDir = this.getServletContext().getRealPath("photos/userAvatar/"+ user.getId());
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String destination = "";
        for (Part part: request.getParts()) {
            if (part.getName().equals("avatar")) {
                destination = targetDir + "/" + "avatar.png";
                part.write(destination);
            }
        }
        ServletUtils.redirect("/Account/Profile", request, response);
    }

    private void setDefaultAvatar(int userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetDir = this.getServletContext().getRealPath("photos/userAvatar/"+ userId);
        File userDir = new File(targetDir);
        if (!userDir.exists()) {
            userDir.mkdir();
        }
        userDir = new File(targetDir+"/avatar.png");
        String defaultDir = this.getServletContext().getRealPath("photos/userAvatar/defaultAvatar.jpg");
        File defaultAvaDir = new File(defaultDir);


        Files.copy(defaultAvaDir.toPath(), userDir.toPath());
    }


    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String oldPassword = request.getParameter("oldPassword");
        Users sessionUser = (Users) session.getAttribute("authUser");
        BCrypt.Result result = BCrypt.verifyer().verify(oldPassword.toCharArray(), sessionUser.getPassword());

       if (result.verified) {
           String rawpwd = request.getParameter("rawpwd");
           String bcryptHashString = BCrypt.withDefaults().hashToString(12, rawpwd.toCharArray());

           usersDAO.changePassword(sessionUser.getId(), bcryptHashString);
           request.setAttribute("errorChangePass", "Thay đổi mật khẩu thành công!");
           ServletUtils.redirect("/Account/Profile",request,response);
       } else {
           request.setAttribute("errorChangePass", "Mật khẩu cũ không chính xác, vui lòng thử lại!");
           ServletUtils.forward("/views/vwAccount/Password.jsp", request, response);
       }
    }

    private void editProfileUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        Users user = (Users) request.getSession().getAttribute("authUser");
        String fullName = request.getParameter("newFullName");
        String email = request.getParameter("newEmail");


        int role = user.getRole();
        try {
            role = Integer.parseInt(request.getParameter("role"));
        }catch (NumberFormatException e) {}

        LocalDateTime dob = user.getDob();
        if (!request.getParameter("newDob").equals("__/__/____"))
        {
            String strDob = request.getParameter("newDob") + " 00:00";
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dob = LocalDateTime.parse(strDob, df);
        }

        usersDAO.updateProfile(user.getId(), fullName, role, email, dob);
        request.getSession().setAttribute("authUser", usersDAO.findById(user.getId()));
        ServletUtils.redirect("/Account/Profile", request, response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String rawpwd = request.getParameter("rawpwd");
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, rawpwd.toCharArray());

        String strDob = request.getParameter("dob") + " 00:00";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dob = LocalDateTime.parse(strDob, df);

        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        int role =0;
        try {
            role = Integer.parseInt(request.getParameter("role"));
        }catch (NumberFormatException e) {
        }


        Users c = new Users();
        if (role==1) {
            c = new Users(0, username,bcryptHashString, fullName, LocalDateTime.now(), 7*24*60,1,dob, email, null,null );
        } else {
            c = new Users(0, username,bcryptHashString, fullName, LocalDateTime.now(), 0,role,dob, email, null,null );
        }
        
        int userId = usersDAO.add(c);
        setDefaultAvatar(userId, request, response);
        ServletUtils.redirect("/Account/Login",request,response);
    }
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Users user = usersDAO.findByUsername(username);

        if (user != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (result.verified) {
                HttpSession session = request.getSession();
                session.setAttribute("auth", true);
                session.setAttribute("authUser", user);

                this.errorLogin = "";
                ServletUtils.redirect("/Home", request, response);
            } else {
                this.errorLogin = "Thông tin đăng nhập không chính xác";
                ServletUtils.redirect("/Account/Login", request,response);
            }
        } else {
            this.errorLogin = "Thông tin đăng nhập không chính xác";
            ServletUtils.redirect("/Account/Login", request,response);
        }
    }
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("auth", false);
        session.setAttribute("authUser", new Users());

        String url = request.getHeader("referer");
        if (url == null)
            url = "/Home";
        ServletUtils.redirect(url, request, response);
    }
}
