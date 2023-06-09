package com.messi.king.messinews.model.bean;

import com.messi.king.messinews.model.dao.ArticlesDAO;
import com.messi.king.messinews.model.dao.CategoriesDAO;
import com.messi.king.messinews.model.daoimpl.ArticlesDAOImpl;
import com.messi.king.messinews.model.daoimpl.CategoriesDAOImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Users {
    private int id;
    private String username;
    private String password;
    private String full_name;
    private LocalDateTime issue_at;
    private int expiration;
    private int roles_id;
    private LocalDateTime dob;
    private String email;
    private String otp;
    private LocalDateTime otp_exp;

    public Users(int id, String username, String password, String full_name, LocalDateTime issue_at, int expiration, int role, LocalDateTime dob, String email, String otp, LocalDateTime otp_exp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.issue_at = issue_at;
        this.expiration = expiration;
        this.roles_id = role;
        this.dob = dob;
        this.email = email;
        this.otp = otp;
        this.otp_exp = otp_exp;
    }

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public LocalDateTime getIssue_at() {
        return issue_at;
    }

    public void setIssue_at(LocalDateTime issue_at) {
        this.issue_at = issue_at;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public int getRole() {
        return roles_id;
    }

    public void setRole(int role) {
        this.roles_id = role;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtp_exp() {
        return otp_exp;
    }
    public int publishArticlesCount() {
        int count =0;
        ArticlesDAOImpl articlesDAO = new ArticlesDAOImpl();
        List<Articles> arts = articlesDAO.findByWriterId(this.id);
        for (Articles art : arts) {
            if (art.getPublish_date()!=null)
                count++;
        }
        return count;
    }
    public int denyArticlesCount() {
        int count =0;
        ArticlesDAOImpl articlesDAO = new ArticlesDAOImpl();
        List<Articles> arts = articlesDAO.findByWriterId(this.id);
        for (Articles art : arts) {
            if (art.getStatus()==0)
                count++;
        }
        return count;
    }

    public int premiumCount() {
        int count =0;
        ArticlesDAOImpl articlesDAO = new ArticlesDAOImpl();
        List<Articles> arts = articlesDAO.findByWriterId(this.id);
        for (Articles art : arts) {
            if (art.getPremium()==1)
                count++;
        }
        return count;
    }
    public int sumViews() {
        int views =0;
        ArticlesDAOImpl articlesDAO = new ArticlesDAOImpl();
        List<Articles> arts = articlesDAO.findByWriterId(this.id);
        for (Articles art : arts) {
            views+=art.getViews();
        }
        return views;
    }
    public long expirationDate() {
        Duration duration = Duration.between(LocalDateTime.now(), this.getIssue_at().plusMinutes(expiration));
        return  duration.toDays();
    }
    public int checkExpiration() {
        if (this.getRole()==1) {
            Duration duration = Duration.between(LocalDateTime.now(), this.getIssue_at().plusMinutes(expiration));
            System.out.println(duration.toDays());
            return duration.toDays() < 0 ? 0:1;
        } else
            return 1;
    }

}
