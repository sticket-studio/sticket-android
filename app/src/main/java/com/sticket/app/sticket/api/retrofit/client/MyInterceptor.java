package com.sticket.app.sticket.api.retrofit.client;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header("User-Agent", "Beongaeman Android/1.1")
                .method(chain.request().method(), chain.request().body())
                .build();

        long t1 = System.nanoTime();
        Log.i("okhttp", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Log.i("okhttp", ",,"+request.url());
        Log.i("okhttp", ",,"+chain.connection());
        Log.i("okhttp", ",,"+request.headers());

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.i("okhttp", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}