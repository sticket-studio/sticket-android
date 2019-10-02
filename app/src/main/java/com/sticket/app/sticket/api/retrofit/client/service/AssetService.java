package com.sticket.app.sticket.api.retrofit.client.service;

import com.sticket.app.sticket.api.retrofit.dto.request.auth.SignupRequest;
import com.sticket.app.sticket.database.entity.Asset;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YangHC on 2017-08-08.
 */

public interface AssetService {

    @GET("sticket/api/normal/assets")
    Call<List<Asset>> getAllAssets();

    @GET("sticket/api/normal/assets/{assetId}")
    Call<Asset> getAssetById(@Path("assetId") int assetId);

    @GET("sticket/api/normal/assets/{assetId}")
    Call<Asset> searchAsset(@Path("assetId") int assetId);

}
