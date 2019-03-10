package com.sticket.app.sticket.util;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        createDirectory();

        MyBitmapFactory.getInstance().build(getApplicationContext());

        Alert.build(getApplicationContext());
        Preference.getInstance().build(getApplicationContext());
    }

    public void createDirectory() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/sticket");
        Log.e(TAG,"dir name : " + dir.getName());

        if (!dir.exists()) {
            if(dir.mkdirs()){
                Log.e(TAG, "폴더 생성 SUCCESS");
            }else{
                Log.e(TAG, "폴더 생성 FAIL");
            }
        } else {
            Log.e(TAG, "폴더 이미 있음");
        }
    }
}
