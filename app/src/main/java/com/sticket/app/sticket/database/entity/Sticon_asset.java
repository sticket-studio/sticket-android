package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "sticon_asset", foreignKeys = {
                                        @ForeignKey(entity = Asset.class,
                                            parentColumns ="idx",
                                            childColumns = "asset_idx",
                                            onDelete = CASCADE),
                                        @ForeignKey(entity = Sticon.class,
                                            parentColumns = "idx",
                                            childColumns = "sticon_idx",
                                            onDelete = CASCADE)})
public class Sticon_asset {

    @PrimaryKey(autoGenerate = true)
    private int idx;

    @ColumnInfo(name="sticon_idx")
    private int sticon_idx;

    @ColumnInfo(name="asset_idx")
    private int asset_idx;

    @ColumnInfo(name="offset_x")
    private double offset_x;

    @ColumnInfo(name="offset_y")
    private double offset_y;

    @ColumnInfo(name="rotate")
    private int rotate;

    @ColumnInfo(name="flip")
    private int flip;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getSticon_idx() {
        return sticon_idx;
    }

    public void setSticon_idx(int sticon_idx) {
        this.sticon_idx = sticon_idx;
    }

    public int getAsset_idx() {
        return asset_idx;
    }

    public void setAsset_idx(int asset_idx) {
        this.asset_idx = asset_idx;
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
}
