package com.example.near.data.API

import com.example.near.data.models.FcmTokenRequest
import com.example.near.data.models.FriendRequest
import com.example.near.data.models.GroupActionRequest
import com.example.near.data.models.GroupCreateRequest
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.SignUpRequest
import com.example.near.data.models.TemplateCreateRequest
import com.example.near.domain.models.User
import com.example.near.domain.models.UserTemplate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {
    @POST("NEAR/signup/account")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<Void>

    @POST("NEAR/login/account")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("NEAR/user/me")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<User>

    @GET("NEAR/user/get")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Query("id") userId: String
    ): Response<User>

    @POST("")
    suspend fun sendFcmToken(
        @Header("Authorization") header: String,
        @Body token: FcmTokenRequest
    ): Response<Void>

    // Action friend

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

    @DELETE("NEAR/user/delete/friend")
    suspend fun removeFriend(
        @Header("Authorization") token: String,
        @Body request: FriendRequest
    ): Response<Void>

    // Group action

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

    // Action Template

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

}