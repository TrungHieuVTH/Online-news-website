package com.messi.king.messinews.model.bean;

public class Status {
    int id;
    String status_name;

    public Status(int id, String status_name) {
        this.id = id;
        this.status_name = status_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
