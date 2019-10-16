package com.sticket.app.sticket.retrofit.client.service;


import com.sticket.app.sticket.models.Stick;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StickService {

    @GET("sticket/api/normal/sticks")
    Call<List<Stick>> getAllSticks();

}
