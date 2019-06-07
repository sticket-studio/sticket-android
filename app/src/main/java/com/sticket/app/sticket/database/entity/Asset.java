package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.sticket.app.sticket.database.EnumLandmarkTypeConverter;
import com.sticket.app.sticket.util.Landmark;

@Entity(tableName = "asset")
public class Asset {
    @PrimaryKey(autoGenerate = true)
    private  int idx;

    @ColumnInfo(name="local_url")
    private String local_url;

    @ColumnInfo(name="img_url")
    private String img_url;

    @ColumnInfo(name="offset_x")
    private double offset_x;

    @ColumnInfo(name="offset_y")
    private double offset_y;

    @ColumnInfo(name="rotate")
    private int rotate;

    @ColumnInfo(name="filp")
    private int flip;

    @ColumnInfo(name="landmark")
    @TypeConverters(EnumLandmarkTypeConverter.class)
    private Landmark landmark;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getLocal_url() {
        return local_url;
    }

    public void setLocal_url(String local_url) {
        this.local_url = local_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getOffset_x() {
        return offset_x;
    }

    public void setOffset_x(double offset_x) {
        this.offset_x = offset_x;
    }

    public double getOffset_y() {
        return offset_y;
    }

    public void setOffset_y(double offset_y) {
        this.offset_y = offset_y;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    public Landmark getLandmark() {
        return landmark;
    }

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }
}
