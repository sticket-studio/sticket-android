package com.sticket.app.sticket.api.retrofit.dto.request.user;

public class AssetLikeRequest {
    private int assetId;
    private int userId;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
