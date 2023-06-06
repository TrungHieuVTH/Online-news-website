package com.messi.king.messinews.model.dao;

import com.messi.king.messinews.model.bean.Articles;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticlesDAO {
    List<Articles> top10AllCate();

    List<Articles> top5AllCateInWeek();

    List<Articles> searchArticles(String key);

    List<Articles> searchArticlesByTitle(String key);

    List<Articles> searchArticlesByAbs(String key);
    List<Articles> searchAriclesByContent(String key);

    List<Articles> newsRelated(int artId);

    List<Articles> newest10PerCate();

    void edit(int id, String title, String abstractContent, String content, int cateId);

    List<Articles> latestNewsAllCate();

    Articles findById(int id);

    List<Articles> findByWriterId(int writerId);

    List<Articles> findByTagId(int tag_id);

    void viewArticle(int id);

    List<Articles> findByPCatId(int parent_cate_id);

    List<Articles> findByCatId(int id);

    List<Articles> findByCatIdPublish(int userId);

    void delete(Articles art);

    int add(Articles articles);

    List<Articles> findByEditorId(int id);

    public List<Articles> findAll();
    public void acceptArticle(int id, LocalDateTime publish_time, int premium, int idCat, int[] tagIds);
    public void declineArticle(int id,String reason);
}

