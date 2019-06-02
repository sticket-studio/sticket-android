package com.sticket.app.sticket.api.retrofit.dto.request.user;

public class SticonLikeRequest {
    private int sticonId;
    private int userId;

    public int getSticonId() {
        return sticonId;
    }

    public void setSticonId(int sticonId) {
        this.sticonId = sticonId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
