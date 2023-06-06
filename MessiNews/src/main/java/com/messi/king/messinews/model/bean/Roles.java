package com.messi.king.messinews.model.bean;

public class Roles {
    int id;
    String role_name;

    public Roles(int id, String role_name) {
        this.id = id;
        this.role_name = role_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String roles_name) {
        this.role_name = roles_name;
    }
}
