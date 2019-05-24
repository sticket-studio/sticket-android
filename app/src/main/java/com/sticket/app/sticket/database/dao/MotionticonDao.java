package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Motionticon;
import java.util.List;

@Dao
public interface MotionticonDao {

    @Query("SELECT * FROM motionticon")
    List<Motionticon> getAllMotionticons();

    @Query("SELECT * FROM motionticon WHERE idx IN (:idx)")
    List<Motionticon> loadAllByIds(int[] idx);

    @Insert
    void insert(Motionticon... motionticon);

    @Update
    void update(Motionticon... motionticon);

    @Delete
    void delete(Motionticon... motionticon);
}
