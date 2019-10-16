package com.sticket.app.sticket.retrofit.dto.response.user;

public class UserSimple {
    private int id;
    private String name;
    private String email;
    private String imgUrl;
    private String description;
    private int worksCnt;
    private int followerCnt;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWorksCnt() {
        return worksCnt;
    }

    public void setWorksCnt(int worksCnt) {
        this.worksCnt = worksCnt;
    }

    public int getFollowerCnt() {
        return followerCnt;
    }

    public void setFollowerCnt(int followerCnt) {
        this.followerCnt = followerCnt;
    }
}
