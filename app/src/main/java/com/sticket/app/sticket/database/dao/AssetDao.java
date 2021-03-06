package com.sticket.app.sticket.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.util.Landmark;

import java.util.List;

@Dao
public interface AssetDao {

    @Query("SELECT * FROM asset")
    List<Asset> getAllassets();

    @Query("SELECT * FROM asset WHERE idx = (:idx)")
    Asset getAssetById(int idx);

    @Query("SELECT * FROM asset WHERE landmark = (:landmark)")
    List<Asset> getAssetsByLandmark(String landmark);

    @Query("SELECT a.* FROM asset a INNER JOIN sticon_asset sa ON a.idx = sa.asset_idx " +
            "WHERE sa.sticon_idx = (:sticonIdx)")
    Asset getAssetsBySticonId(int sticonIdx);

    @Query("SELECT * FROM asset WHERE idx IN (:idx)")
    List<Asset> loadAllByIds(int[] idx);

    @Insert
    void insert(Asset... asset);

    @Insert
    void insertAll(List<Asset> assets);

    @Update
    void update(Asset... asset);

    @Delete
    void delete(Asset... asset);
}
