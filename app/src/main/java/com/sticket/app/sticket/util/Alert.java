package com.sticket.app.sticket.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by YangHC on 2017-07-29.
 */

public class Alert {
    public static Context context;

    public static void build(Context context){
        Alert.context = context;
    }

    public static void makeText(String s){
        try {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("ALERT_EXCEPTION",e.toString());
        }
    }

    public static void terminate(){
        context = null;
    }
}
