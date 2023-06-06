package com.messi.king.messinews.controllers;

import com.messi.king.messinews.model.bean.Articles;
import com.messi.king.messinews.model.bean.Categories;
import com.messi.king.messinews.model.bean.Tags;
import com.messi.king.messinews.model.bean.Users;
import com.messi.king.messinews.model.daoimpl.ArticlesDAOImpl;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;
import com.messi.king.messinews.model.daoimpl.TagsDAOImpl;
import com.messi.king.messinews.utils.PdfUtils;
import com.messi.king.messinews.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AdminArticlesServlet", value = "/Admin/Articles/*")
public class AdminArticlesServlet extends HttpServlet {

    ArticlesDAOImpl articlesDAO = new ArticlesDAOImpl();
    TagsDAOImpl tagsDAO = new TagsDAOImpl();
    CategoriesDAOImpl categoriesDAO = new CategoriesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();

        switch (url) {
            case "/Upload":
                List<Tags> tagsList = tagsDAO.findAll();
                request.setAttribute("tags", tagsList);
                ServletUtils.forward("/views/vwWriter/Upload.jsp",request,response);
                break;
            case "/List":
                List<Articles> artsComplete = articlesDAO.findAll();
                request.setAttribute("articlesList", artsComplete);
                ServletUtils.forward("/views/vwAdmin/AdminArticleList.jsp", request, response);
                break;
            case "/Accept":
                getAccept(request,response);
                break;
            case "/Deny":
                getDeny(request,response);
                break;
            case "/ViewArticle":
                getViewArticle(request,response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
        }
    }

    private void getAccept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Tags> tagsList = tagsDAO.findAll();
        Articles article = articlesDAO.findById(id);
        List<Categories> catList = categoriesDAO.findAll();

        request.setAttribute("Categories", catList);
        request.setAttribute("article", article);
        request.setAttribute("tags", tagsList);
        ServletUtils.forward("/views/vwAdmin/Accept.jsp", request, response);
    }

    private void getDeny(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Articles article = articlesDAO.findById(id);
        request.setAttribute("article", article);
        ServletUtils.forward("/views/vwAdmin/Deny.jsp", request, response);
    }
    private void getViewArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Articles article = articlesDAO.findById(id);
        List<Tags> tags = tagsDAO.findTagByArticle(id);

        request.setAttribute("article", article);
        request.setAttribute("tags", tags);
        ServletUtils.forward("/views/vwAdmin/ViewArticle.jsp", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();
        switch (url) {
            case "/Accept":
                acceptArticle(request,response);
                break;
            case "/Deny":
                denyArticle(request,response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
        }
    }

    private void denyArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            ServletUtils.redirect("/views/204.jsp", request, response);
            return;
        }

        String reason = "Chưa viết lý do";
        try{
            reason = request.getParameter("reason").trim();
        }catch (NumberFormatException ignored){}

        articlesDAO.declineArticle(id, reason);
        ServletUtils.redirect("/Admin/Articles/List", request, response);

    }

    public void acceptArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = 0, kinds_id = 0, idCat=0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            idCat = Integer.parseInt(request.getParameter("idCat"));
            kinds_id = Integer.parseInt(request.getParameter("kinds_id"));
        } catch (NumberFormatException e) {
            ServletUtils.redirect("/views/204.jsp", request, response);
            return;
        }

        String publish_timeStr = request.getParameter("publish_time") + " 00:00";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime publish_time = LocalDateTime.parse(publish_timeStr, df);

        String[] tagsIdStr = request.getParameter("listTagId").split(",");
        int[] tagsId = Arrays
                .stream(tagsIdStr)
                .mapToInt(Integer::parseInt)
                .toArray();

        articlesDAO.acceptArticle(id, publish_time, kinds_id, idCat, tagsId);

        ServletUtils.redirect("/Admin/Articles/List", request, response);
    }
}
