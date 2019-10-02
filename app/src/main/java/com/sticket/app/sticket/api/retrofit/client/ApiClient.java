package com.sticket.app.sticket.api.retrofit.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YangHC on 2017-08-08.
 */

public class ApiClient {
    private static final String PROD = ApiConfig.API_SERVER_HOST;

    private static ApiClient instance;
    private MyInterceptor interceptor;

    private final AuthService authService;
    private final AssetService assetService;

    public ApiClient() {
        interceptor = new MyInterceptor();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss ZZZ")
                .create();

        OkHttpClient okHttp = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("http://%s:8080/", PROD))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttp)
                .build();

        authService = retrofit.create(AuthService.class);
        assetService = retrofit.create(AssetService.class);
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }

    public AssetService getAssetService() {
        return assetService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setToken(String token){
        interceptor.setToken(token);
    }
}
