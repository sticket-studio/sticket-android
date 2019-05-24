package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "asset",
        foreignKeys = @ForeignKey(entity = Landmark.class,
                        parentColumns = "landmark",
                        childColumns = "landmark"))

public class Asset {
    @PrimaryKey
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
    private int lanmark;

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

    public int getLanmark() {
        return lanmark;
    }

    public void setLanmark(int lanmark) {
        this.lanmark = lanmark;
    }
}
