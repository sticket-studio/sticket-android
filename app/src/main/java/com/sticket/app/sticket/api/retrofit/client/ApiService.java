package com.sticket.app.sticket.api.retrofit.client;

import com.sticket.app.sticket.api.retrofit.dto.request.user.SignupRequest;
import com.sticket.app.sticket.api.retrofit.dto.response.user.SignInResponse;
import com.sticket.app.sticket.api.retrofit.message.ApiMessasge;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YangHC on 2017-08-08.
 */

public interface ApiService {

    @POST("sticket/api/normal/users/user")
    Call<ApiMessasge> userSignUp(@Body SignupRequest request);

    @POST("sticket/oauth/token")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json"
    })
    Call<SignInResponse> getToken(@Header("Authorization") String auth,
                                  @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("grant_type") String grantType
    );

    @GET("sticket/api/normal/users/my")
    Call<ApiMessasge> getMyInformation(@Path("phoneNum") String phoneNum);

}
