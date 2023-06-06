package com.messi.king.messinews.model.dao;

import com.messi.king.messinews.model.bean.ArticleHasTag;
import com.messi.king.messinews.model.bean.ParentCategories;
import com.messi.king.messinews.model.bean.Tags;
import com.messi.king.messinews.utils.DbUtils;
import org.sql2o.Connection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TagsDAO {
    public Tags findById(int id);
    public List<Tags> findAll();
    public List<Tags> findTagByArticle(int artId);
    public void add(String nameTag);
    public void addTagsByArticle(int artId, int[] tagsId);
    public void editTagsByArticle(int artId, int[] tagsId);
    public void delete(Tags tag);
    public void edit(String nameTag, Tags tag);
}

