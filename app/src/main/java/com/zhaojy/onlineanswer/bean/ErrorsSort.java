package com.zhaojy.onlineanswer.bean;

/**
 * @author: zhaojy
 * @data:On 2019/1/26.
 */
public class ErrorsSort {
    private int id;
    private String name;
    private String iconUrl;
    //错题数量
    private int sum;

    public String getIconUrl() {
        return iconUrl == null ? "" : iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
