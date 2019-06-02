package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName ="motionticon_sticon" ,foreignKeys = {
        @ForeignKey(entity = Motionticon.class,
        parentColumns = "idx",
        childColumns = "motionticon_idx",
        onDelete = CASCADE),
        @ForeignKey(entity = Sticon.class,
        parentColumns = "idx",
        childColumns = "sticon_idx",
        onDelete = CASCADE)
})

public class Motionticon_sticon {

    @PrimaryKey(autoGenerate = true)
    private int idx;

    @ColumnInfo(name="motionticon_idx")
    private int motionticon_idx;

    @ColumnInfo(name="sticon_idx")
    private int sticon_idx;

    @ColumnInfo(name="sequence_num")
    private int sequence_num;

    @ColumnInfo(name="maintain_time")
    private int maintain_time;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getMotionticon_idx() {
        return motionticon_idx;
    }

    public void setMotionticon_idx(int motionticon_idx) {
        this.motionticon_idx = motionticon_idx;
    }

    public int getSticon_idx() {
        return sticon_idx;
    }

    public void setSticon_idx(int sticon_idx) {
        this.sticon_idx = sticon_idx;
    }

    public int getSequence_num() {
        return sequence_num;
    }

    public void setSequence_num(int sequence_num) {
        this.sequence_num = sequence_num;
    }

    public int getMaintain_time() {
        return maintain_time;
    }

    public void setMaintain_time(int maintain_time) {
        this.maintain_time = maintain_time;
    }
}
