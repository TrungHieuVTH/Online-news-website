package com.messi.king.messinews.model.daoimpl;

import com.messi.king.messinews.model.bean.Comments;
import com.messi.king.messinews.model.bean.Tags;
import com.messi.king.messinews.model.dao.CommentDAO;
import com.messi.king.messinews.utils.DbUtils;
import org.sql2o.Connection;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    public void add(int user_id, int article_id, String content) {
        String insertSql = "INSERT INTO comments (user_id, article_id, comment, date) VALUES (:user_id, :article_id, :comment, :date)";
        try (Connection con = DbUtils.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("user_id",user_id)
                    .addParameter("article_id", article_id)
                    .addParameter("comment", content)
                    .addParameter("date", LocalDateTime.now())
                    .executeUpdate();
        }
    }

    public void updateComment(int id, String content) {
        final String query = "update comments set comment = :comment, date= :date where id = :id";
        try (Connection con = DbUtils.getConnection()) {
            con.createQuery(query)
                    .addParameter("id", id)
                    .addParameter("comment", content)
                    .addParameter("date", LocalDateTime.now())
                    .executeUpdate();
        }
    }

    public Comments findById(int id) {
        final String query = "select * from comments where id = :id";
        try (Connection con = DbUtils.getConnection()) {
            Comments comment = con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Comments.class);
            return comment;
        }
    }

    public List<Comments> findByArtId(int artId) {
        final String query = "select * from comments where article_id = :artId";
        try (Connection con = DbUtils.getConnection()) {
            List<Comments> comments = con.createQuery(query)
                    .addParameter("artId", artId)
                    .executeAndFetch(Comments.class);
            return comments;
        }
    }

    public void delete(int commentId) {
        final String query = "delete from comments where id = :commentId";
        try (Connection con = DbUtils.getConnection()) {
            con.createQuery(query)
                    .addParameter("commentId", commentId)
                    .executeUpdate();
        }
    }
}

