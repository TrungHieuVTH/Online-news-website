package com.messi.king.messinews.model.dao;

import com.messi.king.messinews.model.bean.Comments;
import com.messi.king.messinews.model.bean.Tags;
import com.messi.king.messinews.utils.DbUtils;
import org.sql2o.Connection;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentDAO {
    public void add(int user_id, int article_id, String content);
    public void updateComment(int id, String content);
    public Comments findById(int id);
    public List<Comments> findByArtId(int artId);
    public void delete(int commentId);
}

