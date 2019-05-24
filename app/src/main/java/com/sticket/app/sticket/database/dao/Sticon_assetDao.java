package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Sticon_asset;

import java.util.List;

@Dao
public interface Sticon_assetDao {
    @Query("SELECT * FROM sticon_asset")
    List<Sticon_asset> getAllSticon_assets();

    @Query("SELECT * FROM sticon_asset WHERE idx IN (:idx)")
    List<Sticon_asset> loadAllByIds(int[] idx);

    @Insert
    void insert(Sticon_asset sticon_asset);

    @Update
    void update(Sticon_asset sticon_asset);

    @Delete
    void delete(Sticon_asset sticon_asset);
}
