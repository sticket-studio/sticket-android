package com.sticket.app.sticket.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


/**
 * Created by YangHC on 2019-03-04.
 */

public class Preference {
    private static String SHARED_PREFERNECE_NAME = "sticket";

    private static Preference instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private Preference() {
    }

    public void build(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERNECE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static Preference getInstance() {
        if (sharedPreferences == null || instance == null) {
            instance = new Preference();
        }
        return instance;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }


    public void putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.apply();
    }

    public Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0f);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
