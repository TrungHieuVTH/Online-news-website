package com.messi.king.messinews.model.bean;

import com.messi.king.messinews.model.dao.CategoriesDAO;
import com.messi.king.messinews.model.dao.UsersDAO;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;
import com.messi.king.messinews.model.daoimpl.UsersDAOImpl;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Articles implements Comparator<Articles> {
    private int id;
    private String title;
    private LocalDateTime publish_date;
    private int views;
    private String abstract_content;
    private String content;
    private int categories_id;
    private int kinds_id;
    private int writer_id;
    private int status_id;
    private String reason;

    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }

    public Articles(int id, String title, LocalDateTime publish_date, int views, String abstract_content, String content, int categories_id, int premium, int writer_id, int status, String reason) {
        this.id = id;
        this.title = title;
        this.publish_date = publish_date;
        this.views = views;
        this.abstract_content = abstract_content;
        this.content = content;
        this.categories_id = categories_id;
        this.kinds_id = premium;
        this.writer_id = writer_id;
        this.status_id = status;
        this.reason = reason;
    }

    public Articles() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDateTime publish_date) {
        this.publish_date = publish_date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getAbstract_content() {
        return abstract_content;
    }

    public void setAbstract_content(String abstract_content) {
        this.abstract_content = abstract_content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

    public int getPremium() {
        return kinds_id;
    }

    public void setPremium(int premium) {
        this.kinds_id = premium;
    }

    public int getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(int writer_id) {
        this.writer_id = writer_id;
    }

    public int getStatus() {
        return status_id;
    }

    public void setStatus(int status) {
        this.status_id = status;
    }
    public String getWriterName(int id) {
        UsersDAOImpl usersDAO = new UsersDAOImpl();
        return usersDAO.findById(id).getFull_name();
    }
    public String getCategoriesName(int id) {
        CategoriesDAOImpl categoriesDAO = new CategoriesDAOImpl();
        return categoriesDAO.findById(id).getName_category();
    }
    @Override
    public int compare(Articles o1, Articles o2) {
        return o2.getPremium() - o1.getPremium() ;
    }
}
