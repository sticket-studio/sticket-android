package com.sticket.app.sticket.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "motionticon")
public class Motionticon {

    @PrimaryKey
    private int idx;

    @ColumnInfo(name="img_url")
    private String img_url;

    @ColumnInfo(name="local_url")
    private String local_url;

}
