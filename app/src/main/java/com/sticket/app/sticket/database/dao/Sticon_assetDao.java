package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.SticonAsset;

import java.util.List;

@Dao
public interface Sticon_assetDao {
    @Query("SELECT * FROM Sticon_asset")
    List<SticonAsset> getAllSticon_assets();

    @Query("SELECT * FROM Sticon_asset WHERE idx IN (:idx)")
    List<SticonAsset> loadAllByIds(int[] idx);

    @Insert
    void insert(SticonAsset... sticon_asset);

    @Update
    void update(SticonAsset... sticon_asset);

    @Delete
    void delete(SticonAsset... sticon_asset);
}
