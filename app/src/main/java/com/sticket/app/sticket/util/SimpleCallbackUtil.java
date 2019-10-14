package com.sticket.app.sticket.util;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleCallbackUtil {

    public static <E> Callback<E> getSimpleCallback() {
        return getSimpleCallback(null);
    }

    public static<E> Callback<E> getSimpleCallback(final SimpleCallback<E> simpleCallback) {
        return new Callback<E>() {
            @Override
            public void onResponse(@NonNull Call<E> call,
                                   @NonNull Response<E> response) {
                if (response.body() != null && response.code() == 200) {
                    Alert.makeText("성공");
                    simpleCallback.onSuccess(response.body());
                } else {
                    Alert.makeText("에러");
                }
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                Alert.makeText("요청 중 네트워크에러 발생");
            }
        };
    }

    public interface SimpleCallback<E> {
        public void onSuccess(E responseBody);
    }
}
