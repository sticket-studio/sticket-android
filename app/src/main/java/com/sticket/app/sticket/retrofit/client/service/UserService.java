package com.sticket.app.sticket.retrofit.client.service;

import com.sticket.app.sticket.retrofit.dto.response.user.GetMyFavoriteAuthorsResponse;
import com.sticket.app.sticket.retrofit.message.ApiMessasge;
import com.sticket.app.sticket.models.User;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    /**
     * 내 정보 조회
     *
     * @return 응답받은 User 정보
     */
    @GET("sticket/api/normal/users/me")
    public User getMyInfo();

    /**
     * 유저 정보 조회
     * [작가페이지]
     *
     * @param userId 찾을 User의 Id
     * @return 응답받은 User 정보
     */
    @GET("sticket/api/normal/users/{userId}")
    public User getUserInfoById(@Path("userId") int userId);

    /**
     * 내 정보 수정
     * [마이페이지]
     *
     * 백엔드 수정할 예정 (아직 미작동)
     * @param user 바꿀 User의 정보
     */
    @PUT("sticket/api/normal/users")
    public ApiMessasge updateMyInfo(@Body User user);

    /**
     * 내가 좋아하는 작가 리스트 조회
     * [좋아요 페이지]
     *
     * @return 내가 좋아하는 작가 리스트
     */
    @GET("sticket/api/normal/users/like")
    public GetMyFavoriteAuthorsResponse getMyLikeAuthor();

    /**
     * 작가 좋아요
     * [작가 페이지]
     *
     * @param userId 좋아요 할 작가의 Id
     * @return 성공 여부
     */
    @POST("sticket/api/normal/users/like/{userId}")
    public ApiMessasge likeAuthor(@Path("userId") int userId);

    /**
     * 작가 좋아요 취소
     * [작가 페이지 | 좋아요 페이지]
     * @param userId 좋아요 취소할 작가의 Id
     * @return 성공 여부
     */
    @DELETE("sticket/api/normal/users/like/{userId}")
    public ApiMessasge dislikeAuthor(@Path("userId") int userId);
}