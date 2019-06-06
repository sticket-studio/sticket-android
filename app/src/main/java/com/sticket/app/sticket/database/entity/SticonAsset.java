package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "sticon_asset", foreignKeys = {
        @ForeignKey(entity = Asset.class,
                parentColumns = "idx",
                childColumns = "asset_idx",
                onDelete = CASCADE),
        @ForeignKey(entity = Sticon.class,
                parentColumns = "idx",
                childColumns = "sticon_idx",
                onDelete = CASCADE)})
public class SticonAsset {

    @PrimaryKey(autoGenerate = true)
    private int idx;

    @ColumnInfo(name = "sticon_idx")
    private int sticonIdx;

    @ColumnInfo(name = "asset_idx")
    private int assetIdx;

    @ColumnInfo(name = "offset_x")
    private double offsetX;

    @ColumnInfo(name = "offset_y")
    private double offsetY;

    @ColumnInfo(name = "rotate")
    private int rotate;

    @ColumnInfo(name = "flip")
    private int flip;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getSticonIdx() {
        return sticonIdx;
    }

    public void setSticonIdx(int sticonIdx) {
        this.sticonIdx = sticonIdx;
    }

    public int getAssetIdx() {
        return assetIdx;
    }

    public void setAssetIdx(int assetIdx) {
        this.assetIdx = assetIdx;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
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
}
