package com.sticket.app.sticket.activities.store.store_like;

import android.graphics.drawable.Drawable;

public class LikeAuthorItem {
    private int id;
    private String userImg;
    private String userName;
    private int workCount;
    private String title;
    private String likeNum;

    public LikeAuthorItem(int id, String userImg, String userName, int workCount, String title, String likeNum) {
        this.id = id;
        this.userImg = userImg;
        this.userName = userName;
        this.workCount = workCount;
        this.title = title;
        this.likeNum = likeNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
}