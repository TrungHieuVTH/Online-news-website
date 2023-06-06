package com.messi.king.messinews.controllers;

import com.messi.king.messinews.model.bean.*;

import com.messi.king.messinews.model.daoimpl.ArticlesDAOImpl;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;
import com.messi.king.messinews.model.daoimpl.TagsDAOImpl;
import com.messi.king.messinews.utils.PdfUtils;
import com.messi.king.messinews.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "WriterServlet", value = "/Writer/*")
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 *1024,
        maxFileSize = 50*1024*1024,
        maxRequestSize = 50*1024*1024
)
public class WriterServlet extends HttpServlet {

    ArticlesDAOImpl articlesDAO = new ArticlesDAOImpl();
    TagsDAOImpl tagsDAO = new TagsDAOImpl();
    CategoriesDAOImpl categoriesDAO = new CategoriesDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getPathInfo();
        HttpSession session = request.getSession();
        switch (url) {
            case "/List":
                Users user = (Users) session.getAttribute("authUser");
                List<Articles> articlesList = articlesDAO.findByWriterId(user.getId());
                request.setAttribute("articlesList", articlesList);

                ServletUtils.forward("/views/vwWriter/List.jsp",request,response);
                break;
            case "/Upload":
                List<Tags> tagsList = tagsDAO.findAll();
                request.setAttribute("tags", tagsList);
                ServletUtils.forward("/views/vwWriter/Upload.jsp",request,response);
                break;
            case "/Edit":
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException e) {
                }
                Articles art = articlesDAO.findById(id);
                List<Tags> allTags = tagsDAO.findAll();
                List<Tags> tags = tagsDAO.findTagByArticle(id);
                Categories categories = categoriesDAO.findById(art.getCategories_id());

                request.setAttribute("art", art);
                request.setAttribute("allTags", allTags);
                request.setAttribute("tags", tags);
                request.setAttribute("categories", categories);

                ServletUtils.forward("/views/vwWriter/Edit.jsp",request,response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();
        HttpSession session = request.getSession();
        switch (url) {
            case "/Upload":
                upload(request,response);
                break;
            case "/Edit":
                edit(request,response);
                break;
            case "/EditBackground":
                editImage("backgroundMain",request,response);
                break;
            case "/EditMain":
                editImage("imgMain",request,response);
                break;
            case "/Delete":
                delete(request,response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
        }
    }

    private void editImage(String type, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        Articles art = articlesDAO.findById(id);
        if (art!=null) {
            String targetDir = this.getServletContext().getRealPath("photos/articles/"+id);
            String destination = "";
            for (Part part: request.getParts()) {
                if (part.getName().equals(type)) {
                    destination = targetDir + "/" + (type.equals("imgMain") ? "a.png" : "b.png");
                    part.write(destination);
                }
            }
            ServletUtils.redirect("/Writer/List", request, response);
        } else
            ServletUtils.forward("/views/204.jsp",request,response);
    }



    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        Articles art = articlesDAO.findById(id);
        if (art!=null) {
            articlesDAO.delete(art);
            ServletUtils.redirect("/Writer/List", request, response);
        } else
            ServletUtils.forward("/views/204.jsp",request,response);

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = 0;

        String title = request.getParameter("title");
        String abstractContent = request.getParameter("abstract");
        String content = request.getParameter("content");
        String[] listTagsID = request.getParameter("listTagId").split(",");;

        int[] tagsId = Arrays
                .stream(listTagsID)
                .mapToInt(Integer::parseInt)
                .toArray();
        int cateId = 0;
        try {
            cateId = Integer.parseInt(request.getParameter("cateId"));
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        Articles art = articlesDAO.findById(id);
        if (art!=null) {
            articlesDAO.edit(id, title, abstractContent, content, cateId);
            tagsDAO.editTagsByArticle(id, tagsId);
            ServletUtils.redirect("/Writer/List", request, response);
        } else
            ServletUtils.forward("/views/204.jsp",request,response);
    }

    private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("authUser");

        String title = request.getParameter("title");
        String abstractContent = request.getParameter("abstract");
        String content = request.getParameter("content");

        String[] listTagsID = request.getParameter("listTagId").split(",");;

        int[] tagsId = Arrays
                .stream(listTagsID)
                .mapToInt(Integer::parseInt)
                .toArray();

        int cateId = 0;
        try {
            cateId = Integer.parseInt(request.getParameter("cateId"));
        } catch (NumberFormatException e) {
        }

        int artId = articlesDAO.add(new Articles(0,title,null,0,abstractContent,content,cateId,0,((Users)request.getSession().getAttribute("authUser")).getId(),-1, null));

        tagsDAO.addTagsByArticle(artId, tagsId);

        String targetDir = this.getServletContext().getRealPath("photos/articles/"+artId);
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String destination = "";
        for (Part part: request.getParts()) {
            if (part.getName().equals("imgMain")) {
                destination = targetDir + "/" + "a.png";
                part.write(destination);
            }
            if (part.getName().equals("backgroundMain")) {
                destination = targetDir + "/" + "b.png";
                part.write(destination);
            }
        }
        if (user.getRole()==4) {
            int premium = 0;
            try {
                premium = Integer.parseInt(request.getParameter("premium"));
            } catch (NumberFormatException e) {
                ServletUtils.redirect("/views/204.jsp", request, response);
            }

            String publish_timeStr = request.getParameter("publish_time") + " 00:00";
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime publish_time = LocalDateTime.parse(publish_timeStr, df);

            articlesDAO.acceptArticle(artId, publish_time, premium,cateId, tagsId);
//            Articles art = articlesDAO.findById(artId);
//            PdfUtils.createPdfFile(art, request, response);
            ServletUtils.redirect("/Admin/Articles/List", request, response);
            return;
        }
        ServletUtils.redirect("/Writer/List", request, response);
    }
}
