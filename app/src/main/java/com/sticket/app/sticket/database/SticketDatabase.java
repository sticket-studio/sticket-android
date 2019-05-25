package com.sticket.app.sticket.database;


import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.sticket.app.sticket.database.dao.AssetDao;
import com.sticket.app.sticket.database.dao.LandmarkDao;
import com.sticket.app.sticket.database.dao.MotionticonDao;
import com.sticket.app.sticket.database.dao.Motionticon_sticonDao;
import com.sticket.app.sticket.database.dao.SticonDao;
import com.sticket.app.sticket.database.dao.Sticon_assetDao;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Sticon_asset;
import com.sticket.app.sticket.database.entity.Landmark;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Motionticon_sticon;
import com.sticket.app.sticket.database.entity.Sticon;

@android.arch.persistence.room.Database(entities = {Asset.class, Sticon_asset.class,
        Landmark.class, Motionticon.class, Motionticon_sticon.class, Sticon.class}, version = 3)
public abstract  class SticketDatabase extends RoomDatabase {

    private static SticketDatabase INSTANCE;

    public abstract AssetDao assetDao();
    public abstract SticonDao sticonDao();
    public abstract MotionticonDao motionticonDao();
    public abstract Sticon_assetDao sticon_assetDao();
    public abstract Motionticon_sticonDao motionticon_sticonDao();
    public abstract LandmarkDao landmarkDao();


    public static SticketDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SticketDatabase.class,"sticket_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE= null;
    }




    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
