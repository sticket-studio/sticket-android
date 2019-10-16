package com.sticket.app.sticket.retrofit.client;

import android.util.Log;

import com.sticket.app.sticket.util.Alert;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class CustomCallback<T> implements Callback<T> {

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Alert.makeText("네트워크 에러 발생");
        Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()), t);
    }
}
