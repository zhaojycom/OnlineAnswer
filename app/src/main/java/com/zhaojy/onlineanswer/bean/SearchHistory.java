package com.zhaojy.onlineanswer.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author: zhaojy
 * @data:On 2018/12/30.
 */
@Entity
public class SearchHistory {
    @Id
    private long id;
    private String keyword;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword == null ? "" : keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
