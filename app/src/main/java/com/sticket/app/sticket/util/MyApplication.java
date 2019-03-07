package com.sticket.app.sticket.util;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MyApplication extends Application {
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
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "testDir");

        if (!dir.exists()) {
            if(dir.mkdirs()){
                Alert.makeText("폴더 생성 SUCCESS");
            }else{
                Alert.makeText("폴더 생성 FAIL");
            }
        } else {
            Alert.makeText("폴더 이미 있음");
        }
    }
}
