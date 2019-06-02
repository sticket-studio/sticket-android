package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Landmark;

import java.util.List;

@Dao
public interface LandmarkDao {
    @Query("SELECT * FROM landmark")
    List<Landmark> getAlllandmarks();

    @Query("SELECT * FROM landmark WHERE idx IN (:idx)")
    List<Landmark> loadAllByIds(int[] idx);

    @Insert
    void insert(Landmark... landmarks);

    @Update
    void update(Landmark... landmarks);

    @Delete
    void delete(Landmark... landmarks);
}
