package com.sticket.app.sticket;

import android.app.Application;
import android.os.Environment;

import com.sticket.app.sticket.database.InitBasicAssets;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.ApiConfig;
import com.sticket.app.sticket.retrofit.dto.request.auth.SignInRequest;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.MyBitmapFactory;
import com.sticket.app.sticket.util.Preference;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

import okhttp3.Credentials;

import static android.app.Activity.RESULT_OK;

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

        if (Preference.getInstance().getBoolean(Preference.PREFERENCE_NAME_FIRST_LAUNCH)) {
            Preference.getInstance().putString(Preference.PREFERENCE_NAME_SAVE_LOCATION,
                    Environment.getExternalStorageDirectory().getAbsolutePath());
        }

        FileUtil.buildPaths();

        String storedEmail = Preference.getInstance().getString(Preference.PREFERENCE_NAME_EMAIL);
        String storedPassword = Preference.getInstance().getString(Preference.PREFERENCE_NAME_PASSWORD);

        if (storedEmail != null && storedPassword != null) {
            ApiClient.getInstance().getAuthService()
                    .getToken(Credentials.basic(ApiConfig.USER_NAME, ApiConfig.USER_SECRET),
                            storedEmail, storedPassword, SignInRequest.GRANT_TYPE)
                    .enqueue(SimpleCallbackUtil.getSimpleCallback(signinResponse -> {
                        ApiClient.getInstance().setToken(signinResponse.getAccessToken());
                        ApiClient.getInstance().setUserId(signinResponse.getUserId());
                    }));
        }
    }
}
