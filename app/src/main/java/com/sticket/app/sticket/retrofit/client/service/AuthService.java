package com.sticket.app.sticket.retrofit.client.service;

import com.sticket.app.sticket.retrofit.dto.request.auth.FindEmailRequest;
import com.sticket.app.sticket.retrofit.dto.request.auth.FindPasswordRequest;
import com.sticket.app.sticket.retrofit.dto.request.auth.SignupRequest;
import com.sticket.app.sticket.retrofit.dto.request.auth.UpdatePasswordRequest;
import com.sticket.app.sticket.retrofit.dto.response.user.SignInResponse;
import com.sticket.app.sticket.retrofit.message.ApiMessasge;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface AuthService {

    /**
     * 회원가입
     * [회원가입 페이지]
     *
     * @param request 회원가입 정보
     * @return 성공 여부
     */
    @POST("sticket/api/auth/signup")
    Call<ApiMessasge> userSignUp(@Body SignupRequest request);

    /**
     * 토큰 발급 요청
     * [로그인 페이지]
     *
     * @param auth OAuth Client 인증
     * @param username Resource Owner email
     * @param password Resource Owner password
     * @param grantType Grant type 일반적으로 "password"
     * @return 토큰 정보
     */
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

    /**
     * 로그아웃 - 토큰을 invalidate 시킴
     * [마이페이지(?)]
     *
     * @return 성공 여부
     */
    @DELETE("sticket/api/auth/signout")
    Call<ApiMessasge> signout();

    /**
     * 이메일 찾기 (미구현)
     * @param findEmailRequest 이메일을 찾기 위한 정보
     * @return 성공 여부
     */
    @POST("sticket/api/auth/email")
    Call<ApiMessasge> findEmail(@Body FindEmailRequest findEmailRequest);

    /**
     * 비밀번호 찾기
     * [로그인 페이지]
     *
     * @param findPasswordRequest 비밀번호를 찾기 위한 정보
     * @return 성공 여부
     */
    @POST("sticket/api/auth/password")
    Call<ApiMessasge> findPassword(@Body FindPasswordRequest findPasswordRequest);

    /**
     * 비밀번호 변경
     * [마이페이지]
     *
     * @param updatePasswordRequest 새로운 비밀번호
     * @return 성공 여부
     */
    @PUT("sticket/api/auth/password")
    Call<ApiMessasge> updatePassword(@Body UpdatePasswordRequest updatePasswordRequest);

}
