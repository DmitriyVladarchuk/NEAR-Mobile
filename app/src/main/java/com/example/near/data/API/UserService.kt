package com.example.near.data.API

import com.example.near.data.models.FriendRequest
import com.example.near.data.models.GroupActionRequest
import com.example.near.data.models.GroupCreateRequest
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.SignUpRequest
import com.example.near.domain.models.User
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

}