package com.sticket.app.sticket.retrofit.client.service;


import com.sticket.app.sticket.models.Stick;
import com.sticket.app.sticket.retrofit.message.ApiMessasge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StickService {

    @GET("sticket/api/normal/sticks")
    Call<List<Stick>> getAllSticks();

    @POST("sticket/api/normal/sticks/{stickId}/buy")
    Call<ApiMessasge> buyStick(@Path("stickId")int stickId);

}
