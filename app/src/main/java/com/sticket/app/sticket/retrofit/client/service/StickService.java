package com.sticket.app.sticket.retrofit.client.service;


import com.sticket.app.sticket.database.entity.Asset;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StickService {

    @GET("sticket/api/normal/sticks")
    Call<List<Asset>> getAllAssets();

}
