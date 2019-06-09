package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.sticket.app.sticket.database.EnumLandmarkTypeConverter;
import com.sticket.app.sticket.util.Landmark;

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

    @ColumnInfo(name = "ratio")
    private double ratio;

    @ColumnInfo(name = "flip")
    private int flip;

    @ColumnInfo(name = "landmark")
    @TypeConverters(EnumLandmarkTypeConverter.class)
    private Landmark landmark;

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

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
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
