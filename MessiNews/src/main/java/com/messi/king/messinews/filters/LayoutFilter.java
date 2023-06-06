package com.messi.king.messinews.filters;

import com.messi.king.messinews.model.bean.Categories;
import com.messi.king.messinews.model.bean.ParentCategories;
import com.messi.king.messinews.model.dao.CategoriesDAO;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "LayoutFilter")
public class LayoutFilter implements Filter {

    CategoriesDAOImpl categoriesDAO = new CategoriesDAOImpl();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        List<Categories> allCategories = categoriesDAO.findAll();
        List<ParentCategories> allPCategories = categoriesDAO.findAllParent();

        request.setAttribute("allCategories", allCategories);
        request.setAttribute("allPCategories",allPCategories);

        chain.doFilter(request, response);
    }
}
