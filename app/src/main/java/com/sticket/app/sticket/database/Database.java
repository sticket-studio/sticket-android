package com.sticket.app.sticket.database;


import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Sticon_asset;
import com.sticket.app.sticket.database.entity.Landmark;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Motionticon_sticon;
import com.sticket.app.sticket.database.entity.Sticon;

@android.arch.persistence.room.Database(entities = {Asset.class, Sticon_asset.class,
        Landmark.class, Motionticon.class, Motionticon_sticon.class, Sticon.class}, version = 1)
public class Database {

}
