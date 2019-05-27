package com.sticket.app.sticket.util;

import android.app.Application;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        FileUtil.structDirectories();

        MyBitmapFactory.getInstance().build(getApplicationContext());

        Alert.build(getApplicationContext());
        Preference.getInstance().build(getApplicationContext());
    }
}
