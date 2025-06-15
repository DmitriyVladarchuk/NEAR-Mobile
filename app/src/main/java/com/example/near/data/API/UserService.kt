package com.example.near.data.API

import com.example.near.data.shared.models.FcmTokenRequest
import com.example.near.data.models.user.FriendRequest
import com.example.near.data.models.user.GroupActionRequest
import com.example.near.data.models.user.GroupCreateRequest
import com.example.near.data.models.user.TemplateCreateRequest
import com.example.near.data.models.community.CommunityActionRequest
import com.example.near.data.shared.models.LoginRequest
import com.example.near.data.shared.models.LoginResponse
import com.example.near.data.shared.models.NotificationOptionResponse
import com.example.near.data.shared.models.RefreshTokenRequest
import com.example.near.data.models.user.UserResponse
import com.example.near.data.models.user.UserSignUpRequest
import com.example.near.data.models.user.AllFriendsInfoResponse
import com.example.near.data.models.user.UserUpdateRequest
import com.example.near.domain.models.user.UserTemplate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {
    @POST("NEAR/signup/account")
    suspend fun signUp(
        @Body request: UserSignUpRequest
    ): Response<Void>

    @POST("NEAR/login/account")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("NEAR/user/me")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @GET("NEAR/user/get")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Query("id") userId: String
    ): Response<UserResponse>

    @PATCH("NEAR/user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UserUpdateRequest
    ): Response<Void>

    @GET("NEAR/user/get-notification-options")
    suspend fun getNotificationOptions(
        @Header("Authorization") token: String
    ): Response<List<NotificationOptionResponse>>

    @POST("NEAR/token/account")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<LoginResponse>

    @POST("NEAR/user/update-device-token")
    suspend fun sendFcmToken(
        @Header("Authorization") header: String,
        @Body token: FcmTokenRequest
    ): Response<Void>

    // --- Action friend ---

    @GET("NEAR/user/all-friends-info")
    suspend fun getAllFriendsInfo(
        @Header("Authorization") token: String
    ): Response<AllFriendsInfoResponse>

    @POST("NEAR/user/request/friend")
    suspend fun sendFriendRequest(
        @Header("Authorization") token: String,
        @Body request: FriendRequest
    ): Response<Void>

    @POST("NEAR/user/add/friend")
    suspend fun addFriendRequest(
        @Header("Authorization") token: String,
        @Body request: FriendRequest
    ): Response<Void>

    @POST("NEAR/user/reject/friend")
    suspend fun rejectFriendRequest(
        @Header("Authorization") token: String,
        @Body request: FriendRequest
    ): Response<Void>

    //@DELETE("NEAR/user/delete/friend")
    @HTTP(method = "DELETE", path = "NEAR/user/delete/friend", hasBody = true)
    suspend fun removeFriend(
        @Header("Authorization") token: String,
        @Body request: FriendRequest
    ): Response<Void>

    // --- Group action ---

    @POST("NEAR/user/group/create")
    suspend fun createGroup(
        @Header("Authorization") token: String,
        @Body request: GroupCreateRequest
    ): Response<Void>

    @PUT("NEAR/user/group/update")
    suspend fun updateGroup(
        @Header("Authorization") token: String,
        @Body request: GroupActionRequest
    ): Response<Void>

    @DELETE("NEAR/user/group/delete")
    suspend fun deleteGroup(
        @Header("Authorization") token: String,
        @Body request: GroupActionRequest
    ): Response<Void>

    // --- Action Template ---

    @POST("NEAR/user/template/create")
    suspend fun createTemplate(
        @Header("Authorization") token: String,
        @Body request: TemplateCreateRequest
    ): Response<Void>

    @PUT("NEAR/user/template/update")
    suspend fun updateTemplate(
        @Header("Authorization") token: String,
        @Body request: UserTemplate
    ): Response<Void>

    @DELETE("NEAR/user/template/delete")
    suspend fun deleteTemplate(
        @Header("Authorization") token: String,
        @Body request: UserTemplate
    ): Response<Void>

    // --- Action Subscribes

    @POST("NEAR/user/subscribe")
    suspend fun userSubscribe(
        @Header("Authorization") token: String,
        @Body request: CommunityActionRequest
    ): Response<Void>

    @POST("NEAR/user/cancel/subscribe")
    suspend fun userCancelSubscribe(
        @Header("Authorization") token: String,
        @Body request: CommunityActionRequest
    ): Response<Void>

}