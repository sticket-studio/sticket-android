package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Asset;

import java.util.List;

@Dao
public interface AssetDao {

    @Query("SELECT * FROM asset")
    List<Asset> getAllassets();

    @Query("SELECT * FROM asset WHERE idx IN (:idx)")
    List<Asset> loadAllByIds(int[] idx);

    @Insert
    void insert(Asset asset);

    @Update
    void update(Asset asset);

    @Delete
    void delete(Asset asset);
}
