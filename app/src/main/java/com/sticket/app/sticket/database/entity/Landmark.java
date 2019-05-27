package com.sticket.app.sticket.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "landmark")
public class Landmark {

    @PrimaryKey(autoGenerate = true)
    private  int idx;

    @ColumnInfo(name="landmark")
    private  String landmark;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
