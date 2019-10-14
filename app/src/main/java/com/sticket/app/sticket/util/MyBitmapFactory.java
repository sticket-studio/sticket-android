package com.sticket.app.sticket.util;

import android.content.Context;
import android.util.Log;

import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.database.entity.SticonAsset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBitmapFactory {
    private static final String TAG = MyBitmapFactory.class.getSimpleName();
    private static final int NO_IMAGE = -1;
    private static final int LANDMARK_SIZE = 12;

    private static MyBitmapFactory instance;

    private SticketDatabase sticketDatabase;

    private Sticon sticon;
    private Map<SticonAsset, Asset> assetMap;

    private  Asset asset;

    private MyBitmapFactory() {
    }

    public void build(Context context) {
        sticketDatabase = SticketDatabase.getDatabase(context);

        assetMap = new HashMap<>();
    }

    public static MyBitmapFactory getInstance() {
        if (instance == null) {
            synchronized (MyBitmapFactory.class) {
                if (instance == null) {
                    instance = new MyBitmapFactory();
                }
            }
        }
        return instance;
    }

    public Sticon getSticon() {
        return sticon;
    }

    public void setSticon(Sticon sticon) {
        this.sticon = sticon;
        setAssetMap(sticon);
    }

    public void setAsset(Asset asset){
        this.asset = asset;
    }

    private void setAssetMap(Sticon sticon) {
        assetMap.clear();

        List<SticonAsset> sticonAssetList
                = sticketDatabase.sticonAssetDao().getSticonAssetsBySticonIdx(sticon.getIdx());
        for (SticonAsset sticonAsset : sticonAssetList) {
            Asset asset = sticketDatabase.assetDao().getAssetById(sticonAsset.getAssetIdx());
            assetMap.put(sticonAsset, asset);
        }
    }

    public Map<SticonAsset, Asset> getAssetMap() {
        return assetMap;
    }
}
