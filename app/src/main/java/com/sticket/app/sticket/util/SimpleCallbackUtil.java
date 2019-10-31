package com.sticket.app.sticket.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sticket.app.sticket.retrofit.client.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleCallbackUtil {
    private static final String TAG = SimpleCallbackUtil.class.getSimpleName();

    public static <E> Callback<E> getSimpleCallback() {
        return getSimpleCallback(null);
    }

    public static<E> Callback<E> getSimpleCallback(final SimpleCallback<E> simpleCallback) {
        return new Callback<E>() {
            @Override
            public void onResponse(@NonNull Call<E> call,
                                   @NonNull Response<E> response) {
                Log.i(TAG,"code : " + response.code());
                Log.i(TAG, "response.body() != null: "+ (response.body() != null));
                Log.i(TAG,"body : " + response.body());
                Log.i(TAG, "response.code() == 200: "+ (response.code() == 200));
                if (response.body() != null && response.code() == 200) {
                    Log.e(TAG, "성공");
                    if(simpleCallback!=null) {
                        simpleCallback.onSuccess(response.body());
                    }
                } else {
                    Log.e(TAG, "에러");
                    Alert.makeText("에러");
                    ApiClient.getInstance().setUserId(0);
                    ApiClient.getInstance().setToken(null);
                }
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                Log.e(TAG, "Exception:", t);
                Alert.makeText("요청 중 네트워크에러 발생");
            }
        };
    }

    public interface SimpleCallback<E> {
        public void onSuccess(E responseBody);
    }
}
