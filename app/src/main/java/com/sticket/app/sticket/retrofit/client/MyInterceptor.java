package com.sticket.app.sticket.retrofit.client;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class MyInterceptor implements Interceptor {
    private final String TOKEN_TYPE = "Bearer ";
    private String token;

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        if (this.token != null) {
            Log.e("MyInterceptor", "token: "+ token);
            request = chain.request().newBuilder()
                    .addHeader("Authorization", TOKEN_TYPE + this.token)
                    .method(chain.request().method(), chain.request().body())
                    .build();
        }

        Response response = chain.proceed(request);

        try {
            Log.i("okhttp", String.format("Sending request %s on %s\n%s\n%s\n%s",
                    request.url(), chain.connection(), request.headers(), request.body(), request.header("Content-Type")));
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Log.i("okhttp", String.format("Received response for %s in %n%s",
                    buffer.clone().readString(Charset.forName("UTF-8")), response.headers()));
        } catch (Exception e) {
            Log.e("okhttp", "Error: " + e);
        }
        // 이부분 고치지 마세염
        // return chain.proceed(request); 해버리면 요청을 다시 보내게 됨
        // 그럼 logout같은 곳에서 에러남
        // (같은 토큰으로 로그아웃 한 번 하면 token이 invalidate 되는데 한 번 더 보내므로 에러남)
        return response;
    }


    public void setToken(String token) {
        this.token = token;
    }
}