package com.sticket.app.sticket.database;

import android.arch.persistence.room.TypeConverter;

import com.sticket.app.sticket.util.Landmark;

public class EnumLandmarkTypeConverter {
        @TypeConverter
        public Landmark restoreEnum(String enumType){
            return Landmark.valueOf(enumType);
        }

        @TypeConverter
        public String saveEnumToString(Landmark landmark){
            return landmark.name();
        }
}
