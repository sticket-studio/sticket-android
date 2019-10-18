package com.sticket.app.sticket;

import android.app.Application;
import android.os.Environment;

import com.sticket.app.sticket.database.InitBasicAssets;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.MyBitmapFactory;
import com.sticket.app.sticket.util.Preference;

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

        FileUtil.buildPaths();
    }
}
