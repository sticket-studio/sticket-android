package com.sticket.app.sticket.retrofit.dto.response.asset;

import com.sticket.app.sticket.models.Asset;

import java.io.Serializable;

public class SimpleAssetResponse implements Serializable {
    private int id;
    private String imgUrl;
    private String name;
    private String landmark;
    private int authorId;
    private String authorName;
    private int themeId;
    private String themeName;
    private int price;

    public SimpleAssetResponse() {
    }



    public SimpleAssetResponse(int id, String imgUrl, String name, String landmark, int authorId, String authorName, int themeId, String themeName, int price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.landmark = landmark;
        this.authorId = authorId;
        this.authorName = authorName;
        this.themeId = themeId;
        this.themeName = themeName;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static SimpleAssetResponse mapping(Asset asset) {
        return new SimpleAssetResponse(asset.getId(), asset.getImgUrl(), asset.getName(),
                asset.getLandmark(), asset.getAuthor().getId(), asset.getAuthor().getName(),
                asset.getTheme().getId(), asset.getTheme().getName(),asset.getPrice());
    }
}
