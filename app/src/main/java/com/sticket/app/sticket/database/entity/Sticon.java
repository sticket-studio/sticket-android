package com.sticket.app.sticket.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "sticon")
public class Sticon {
    @PrimaryKey(autoGenerate = true)
    private int idx;

    @ColumnInfo(name="img_url")
    private String img_url;

    @ColumnInfo(name="local_url")
    private String local_url;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLocal_url() {
        return local_url;
    }

    public void setLocal_url(String local_url) {
        this.local_url = local_url;
    }
}
