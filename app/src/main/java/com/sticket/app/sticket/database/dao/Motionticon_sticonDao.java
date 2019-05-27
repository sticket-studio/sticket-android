package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Motionticon_sticon;

import java.util.List;

@Dao
public interface Motionticon_sticonDao {
    @Query("SELECT * FROM motionticon_sticon")
    List<Motionticon_sticon> getAllMotionticons();

    @Query("SELECT * FROM motionticon_sticon WHERE idx IN (:idx)")
    List<Motionticon_sticon> loadAllByIds(int[] idx);

    @Insert
    void insert(Motionticon_sticon... motionticon_sticon);

    @Update
    void update(Motionticon_sticon... motionticon_sticon);

    @Delete
    void delete(Motionticon_sticon... motionticon_sticon);
}
