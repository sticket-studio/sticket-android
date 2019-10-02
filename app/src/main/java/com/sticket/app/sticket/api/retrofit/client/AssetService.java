package com.sticket.app.sticket.api.retrofit.client;

import com.sticket.app.sticket.api.retrofit.dto.request.auth.SignupRequest;
import com.sticket.app.sticket.database.entity.Asset;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by YangHC on 2017-08-08.
 */

public interface AssetService {

    @GET("sticket/api/normal/assets")
    Call<List<Asset>> getAllAssets();

}
