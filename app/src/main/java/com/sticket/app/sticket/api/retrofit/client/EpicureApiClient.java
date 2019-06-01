package com.sticket.app.sticket.api.retrofit.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YangHC on 2017-08-08.
 */

public class EpicureApiClient {
    private ApiService apiService;
    private static EpicureApiClient instance;
    private static final String PROD = "www.beongaeman.com";

    public void create(){
        /**
         * Gson 컨버터 이용
         */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApiServer(PROD))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static EpicureApiClient getInstance() {
        if (instance == null) {
            synchronized (EpicureApiClient.class) {
                if (instance == null) {
                    instance = new EpicureApiClient();
                }
            }
        }
        return instance;
    }

    public static String getApiServer(String hostIp) {
        return "http://" + hostIp + ":80";
    }

    public ApiService getApiService() {
        return apiService;
    }
}
