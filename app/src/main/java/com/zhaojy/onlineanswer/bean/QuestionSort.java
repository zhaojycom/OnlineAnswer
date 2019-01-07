package com.zhaojy.onlineanswer.bean;

import java.io.Serializable;

public class QuestionSort implements Serializable {
    private int id;
    private String name;
    private String iconUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl == null ? "" : iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}
