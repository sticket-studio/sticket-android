package com.sticket.app.sticket.util;

import android.app.Application;
import android.os.Environment;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        MyBitmapFactory.getInstance().build(getApplicationContext());

        Alert.build(getApplicationContext());
        Preference.getInstance().build(getApplicationContext());

        if(Preference.getInstance().getBoolean(Preference.PREFERENCE_NAME_FIRST_LAUNCH)){
            Preference.getInstance().putString(Preference.PREFERENCE_NAME_SAVE_LOCATION,
                    Environment.getExternalStorageDirectory().getAbsolutePath());
        }

        FileUtil.structDirectories();
    }
}
