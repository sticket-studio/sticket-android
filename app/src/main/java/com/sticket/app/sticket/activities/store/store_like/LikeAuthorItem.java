package com.sticket.app.sticket.activities.store.store_like;

import android.graphics.drawable.Drawable;

public class LikeAuthorItem {
    private Drawable img;
    private String userName;
    private String workNum;
    private String title;
    private String likeNum;

    public LikeAuthorItem(Drawable img, String userName, String workNum, String title, String likeNum) {
        this.img = img;
        this.userName = userName;
        this.workNum = workNum;
        this.title = title;
        this.likeNum = likeNum;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
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
