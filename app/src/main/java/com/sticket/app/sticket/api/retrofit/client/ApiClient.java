package com.sticket.app.sticket.api.retrofit.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YangHC on 2017-08-08.
 */

public class ApiClient {
    private ApiService apiService;
    private static ApiClient instance;
    private OkHttpClient okclient;
    private static final String PROD = ApiConfig.API_SERVER_HOST;

    public void create(){
        /**
         * Gson 컨버터 이용
         */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss ZZZ")
                .create();

        OkHttpClient okHttp = new OkHttpClient.Builder()
                .addInterceptor(new MyInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApiServer(PROD))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttp)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static String createBasicToken() {
        return Credentials.basic(ApiConfig.USER_NAME, ApiConfig.USER_SECRET);
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

    public static String getApiServer(String hostIp) {
        return "http://" + hostIp + ":8080/";
    }

    public ApiService getApiService() {
        return apiService;
    }
}
