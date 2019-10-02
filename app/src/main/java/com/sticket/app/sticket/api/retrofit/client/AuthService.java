package com.sticket.app.sticket.api.retrofit.client;

import com.sticket.app.sticket.api.retrofit.dto.request.auth.FindEmailRequest;
import com.sticket.app.sticket.api.retrofit.dto.request.auth.FindPasswordRequest;
import com.sticket.app.sticket.api.retrofit.dto.request.auth.SignupRequest;
import com.sticket.app.sticket.api.retrofit.dto.request.auth.UpdatePasswordRequest;
import com.sticket.app.sticket.api.retrofit.dto.response.user.SignInResponse;
import com.sticket.app.sticket.api.retrofit.message.ApiMessasge;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by YangHC on 2017-08-08.
 */

public interface AuthService {

    @POST("sticket/api/auth/signup")
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

    @POST("sticket/api/auth/signout")
    Call<ApiMessasge> signout();

    @POST("sticket/api/auth/email")
    Call<ApiMessasge> findEmail(@Body FindEmailRequest findEmailRequest);

    @POST("sticket/api/auth/password")
    Call<ApiMessasge> findPassword(@Body FindPasswordRequest findPasswordRequest);

    @PUT("sticket/api/auth/password")
    Call<ApiMessasge> updatePassword(@Body UpdatePasswordRequest updatePasswordRequest);

}
