package com.zhaojy.onlineanswer.bean;

import android.content.Intent;

/**
 * @author: zhaojy
 * @data:On 2018/12/20.
 */
public class MyRecyclerBean {
    private int icon;
    private String title;
    private Intent intent;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
