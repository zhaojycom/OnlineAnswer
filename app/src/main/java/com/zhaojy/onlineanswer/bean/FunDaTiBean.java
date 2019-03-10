package com.zhaojy.onlineanswer.bean;

/**
 * @author: zhaojy
 * @data:On 2019/2/25.
 */
public class FunDaTiBean {
    /**
     * 标题
     */
    private String title;
    /**
     * 图片id
     */
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
