package com.sticket.app.sticket.retrofit.dto.request.asset;

import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.util.Landmark;

import java.io.File;
import java.io.Serializable;

public class InsertAssetRequest implements Serializable {
    private File img;
    private String description;
    private Landmark landmark;
    private String name;
    private int price = 3;
    private int themeId;

    public InsertAssetRequest() {
    }

    public InsertAssetRequest(File img, String description, Landmark landmark, String name, int price, int themeId) {
        this.img = img;
        this.description = description;
        this.landmark = landmark;
        this.name = name;
        this.price = price;
        this.themeId = themeId;
    }

    public static InsertAssetRequest mapping(Asset asset){
        return new InsertAssetRequest(new File(asset.getLocalUrl()), null, asset.getLandmark(),
                null, 3, 0);
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Landmark getLandmark() {
        return landmark;
    }

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
}
