package com.messi.king.messinews.model.bean;

import com.messi.king.messinews.model.dao.CategoriesDAO;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;

public class ParentCategories {
    private int id;
    private String name_parent_cate;


    public ParentCategories(int id, String name_parent_cate) {
        this.id = id;
        this.name_parent_cate = name_parent_cate;
    }

    public ParentCategories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_parent_cate() {
        return name_parent_cate;
    }

    public void setName_parent_cate(String name_parent_cate) {
        this.name_parent_cate = name_parent_cate;
    }
    public String getParentCategoriesName(int id) {
        CategoriesDAO categoriesDAO = new CategoriesDAOImpl();
        return categoriesDAO.findPCatById(id).getName_parent_cate();
    }
}
