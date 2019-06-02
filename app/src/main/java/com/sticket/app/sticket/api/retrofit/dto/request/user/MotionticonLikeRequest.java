package com.sticket.app.sticket.api.retrofit.dto.request.user;

public class MotionticonLikeRequest {
    private int motionticonId;
    private int userId;

    public int getMotionticonId() {
        return motionticonId;
    }

    public void setMotionticonId(int motionticonId) {
        this.motionticonId = motionticonId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
