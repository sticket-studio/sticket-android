package com.sticket.app.sticket.models;

import java.util.Date;

public class User {
    private int id;
    private String snsType;
    private String email;
    private String name;
    private Date createTime;
    private String imgUrl;
    private int followerCnt;
    private int followingCnt;
    private int stick;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getFollowerCnt() {
        return followerCnt;
    }

    public void setFollowerCnt(int followerCnt) {
        this.followerCnt = followerCnt;
    }

    public int getFollowingCnt() {
        return followingCnt;
    }

    public void setFollowingCnt(int followingCnt) {
        this.followingCnt = followingCnt;
    }

    public int getStick() {
        return stick;
    }

    public void setStick(int stick) {
        this.stick = stick;
    }
}
