package com.sticket.app.sticket.util;

import android.app.Application;

public class MyApplication extends Application {
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        MyBitmapFactory.getInstance().build(getApplicationContext());
    }
}
