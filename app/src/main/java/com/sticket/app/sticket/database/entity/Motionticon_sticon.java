package com.sticket.app.sticket.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName ="motionticon_sticon" ,foreignKeys = {
        @ForeignKey(entity = Motionticon.class,
        parentColumns = "idx",
        childColumns = "motionticon_idx"),
        @ForeignKey(entity = Sticon.class,
        parentColumns = "idx",
        childColumns = "sticon_idx")
})

public class Motionticon_sticon {

    @PrimaryKey
    private int idx;

    @ColumnInfo(name="motionticon_idx")
    private int motionticon_idx;

    @ColumnInfo(name="sticon_idx")
    private int sticon_idx;

    @ColumnInfo(name="sequence_num")
    private int sequence_num;

    @ColumnInfo(name="maintain_time")
    private int maintain_time;


}
