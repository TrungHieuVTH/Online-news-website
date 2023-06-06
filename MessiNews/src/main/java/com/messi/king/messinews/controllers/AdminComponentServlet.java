package com.messi.king.messinews.controllers;

import com.messi.king.messinews.model.bean.Categories;
import com.messi.king.messinews.model.bean.ParentCategories;
import com.messi.king.messinews.model.bean.Tags;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;
import com.messi.king.messinews.model.daoimpl.TagsDAOImpl;
import com.messi.king.messinews.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminComponentServlet", value = "/Admin/Component/*")
public class AdminComponentServlet extends HttpServlet {

    String check = "1";
    TagsDAOImpl tagsDAO = new TagsDAOImpl();
    CategoriesDAOImpl categoriesDAO = new CategoriesDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String url = request.getPathInfo();
        switch (url) {
            case "/List":
                List<Tags> tags = tagsDAO.findAll();
                request.setAttribute("tags",tags);
                request.setAttribute("check",check);
                ServletUtils.forward("/views/vwAdmin/ArticleComponent.jsp",request,response);
                break;

            case "/AddCate":
                ServletUtils.forward("/views/vwAdmin/CateAdminAdd.jsp",request,response);
                break;
            case "/AddPCate":
                ServletUtils.forward("/views/vwAdmin/PCateAdminAdd.jsp",request,response);
                break;
            case "/AddTag":
                ServletUtils.forward("/views/vwAdmin/TagsAdminAdd.jsp",request,response);
                break;


            case "/EditPCate":
                int idPCate = 0;
                try {
                    idPCate = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException e) {
                }
                ParentCategories pcate = categoriesDAO.findPCatById(idPCate);
                if (pcate!=null) {
                    request.setAttribute("pcate", pcate);
                    ServletUtils.forward("/views/vwAdmin/PCateAdminEdit.jsp",request,response);
                } else {
                    ServletUtils.forward("/views/204.jsp",request,response);
                }
                break;
            case "/EditCate":
                int idCate = 0;
                try {
                    idCate = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException e) {
                }
                Categories cate = categoriesDAO.findById(idCate);
                if (cate!=null) {
                    request.setAttribute("cate", cate);
                    ServletUtils.forward("/views/vwAdmin/CateAdminEdit.jsp",request,response);

                } else {
                    ServletUtils.forward("/views/204.jsp",request,response);
                }
                break;
            case "/EditTag":
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException e) {
                }
                Tags tag = tagsDAO.findById(id);
                if (tag!=null) {
                    request.setAttribute("tag",tag);
                    ServletUtils.forward("/views/vwAdmin/TagsAdminEdit.jsp",request,response);
                } else {
                    ServletUtils.forward("/views/204.jsp",request,response);
                }
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = request.getPathInfo();
        switch (url) {
            case "/AddCate":
                addCategory(request,response);
                break;
            case "/AddPCate":
                addPCategory(request,response);
                break;
            case "/AddTag":
                addTag(request,response);
                break;
            case "/EditPCate":
                editPCate(request,response);
                break;
            case "/EditCate":
                editCate(request,response);
                break;
            case "/EditTag":
                editTag(request,response);
                break;
            case "/DeleteCate":
                deleteCate(request,response);
                break;
            case "/DeletePCate":
                deletePCate(request,response);
                break;
            case "/DeleteTag":
                deleteTag(request,response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp",request,response);
                break;
        }
        ServletUtils.redirect("/Admin/Component/List", request, response);
    }

    private void addPCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String namePCate = request.getParameter("namePCate");
        categoriesDAO.addPCate(namePCate);
        this.check = "1";
    }
    private void editPCate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPCate = 0;
        try {
            idPCate = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        String namePCate = request.getParameter("namePCate");
        categoriesDAO.updatePCate(idPCate, namePCate);
        this.check = "1";
    }
    private void deletePCate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPCate = Integer.parseInt(request.getParameter("idPCate"));
        categoriesDAO.deletePCate(idPCate);
        this.check = "1";
    }


    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nameCate = request.getParameter("nameCate");
        int pcateId =  Integer.parseInt(request.getParameter("pcateId"));
        categoriesDAO.addCate(nameCate, pcateId);
        this.check = "2";
    }
    private void editCate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCate = 0, idPCate = 0;
        try {
            idCate = Integer.parseInt(request.getParameter("idCate"));
            idPCate = Integer.parseInt(request.getParameter("idPCate"));

        } catch (NumberFormatException e) {
        }
        String nameCate = request.getParameter("nameCate");

        categoriesDAO.updateCate(idCate, nameCate, idPCate);
        this.check = "2";
    }
    private void deleteCate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCate = Integer.parseInt(request.getParameter("idCate"));
        categoriesDAO.deleteCate(idCate);
        this.check = "2";
    }


    private void addTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nameTag = request.getParameter("nameTag");
        tagsDAO.add(nameTag);
        this.check = "3";
    }
    private void editTag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        Tags tag = tagsDAO.findById(id);
        if (tag!=null) {
            String nameTag = request.getParameter("nameTag");
            tagsDAO.edit(nameTag, tag);
            this.check = "3";
        } else {
            ServletUtils.forward("/views/204.jsp",request,response);
        }
    }
    private void deleteTag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        Tags tag = tagsDAO.findById(id);
        if (tag!=null) {
            tagsDAO.delete(tag);
            this.check = "3";
        } else {
            ServletUtils.forward("/views/204.jsp",request,response);
        }
    }
}
