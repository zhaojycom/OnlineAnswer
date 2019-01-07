package com.zhaojy.onlineanswer.bean;

/**
 * @author: zhaojy
 * @data:On 2018/10/15.
 */

public class User {
    private String phone;
    private String avatar;
    private String nickname;

    public static User user;
    /**
     * 账户是否发生改变
     */
    private boolean userChange = false;

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    private User() {

    }

    /**
     * 复制
     *
     * @param res
     */
    public static void copy(User res) {
        getInstance();
        user.setPhone(res.getPhone());
        user.setAvatar(res.getAvatar());
        user.setNickname(res.getNickname());
        user.setUserChange(res.isUserChange());
    }

    /**
     * 重置
     */
    public static void reset() {
        getInstance();
        user.setPhone(null);
        user.setAvatar(null);
        user.setNickname(null);
        user.setUserChange(false);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isUserChange() {
        return userChange;
    }

    public void setUserChange(boolean userChange) {
        this.userChange = userChange;
    }
}
