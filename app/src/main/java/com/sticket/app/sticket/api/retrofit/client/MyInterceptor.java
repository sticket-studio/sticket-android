package com.sticket.app.sticket.api.retrofit.client;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class MyInterceptor implements Interceptor {
    private final String TOKEN_TYPE = "Bearer ";
    private String token = "";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", this.token)
                .method(chain.request().method(), chain.request().body())
                .build();

        Log.i("okhttp", String.format("Sending request %s on %s\n%s\n%s\n%s",
                request.url(), chain.connection(), request.headers(), request.body(), request.header("content-type")));

        Response response = chain.proceed(request);

        Log.i("okhttp", String.format("Received response for %s in %n%s",
                response.body().toString(), response.headers()));

        return response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = TOKEN_TYPE + token;
    }
}