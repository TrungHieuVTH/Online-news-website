package com.messi.king.messinews.model.dao;

import com.messi.king.messinews.model.bean.Categories;
import com.messi.king.messinews.model.bean.ParentCategories;
import com.messi.king.messinews.utils.DbUtils;
import org.sql2o.Connection;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoriesDAO {
    public List<Categories> findAll();
    public void addCate(String nameCate, int pcateId);
    public void addPCate(String namePCate);
    public List<Categories> findAllByParentId(int pcatId);
    public List<ParentCategories> findAllParent();
    public Categories findById(int id);
    public void updatePCate(int pcateId, String name_parent_cate);
    public ParentCategories findPCatById(int id);

    public void updateCate(int idCate, String nameCate, int idPCate);
    public void deleteCate(int idCate);

    public void deletePCate(int idPCate);

    public List<Categories> findAllByEditorId(int id);
}

