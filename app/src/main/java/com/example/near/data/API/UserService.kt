package com.example.near.data.API

import com.example.near.data.models.LoginUserRequest
import com.example.near.data.models.LoginUserResponse
import com.example.near.data.models.SignUpRequest
import com.example.near.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("NEAR/signup/account")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<Void>

    @GET("NEAR/user/me")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @POST("NEAR/login/account")
    suspend fun login(
        @Body request: LoginUserRequest
    ): Response<LoginUserResponse>
}