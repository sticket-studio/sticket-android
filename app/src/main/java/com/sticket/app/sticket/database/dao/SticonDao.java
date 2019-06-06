package com.sticket.app.sticket.database.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Sticon;

import java.util.List;

@Dao
public interface SticonDao {

    @Query("SELECT * FROM sticon")
    List<Sticon> getAllSticon();

    @Query("SELECT * FROM asset WHERE idx IN (:idx)")
    List<Sticon> loadAllByIds(int[] idx);

    @Insert
    long insert(Sticon sticon);

    @Delete
    void delete(Sticon... sticon);

    @Update
    void update(Sticon... sticon);


}
