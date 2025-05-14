package com.example.near.data.API

import com.example.near.data.models.FcmTokenRequest
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.community.CommunityResponse
import com.example.near.data.models.community.SignUpCommunityRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CommunityService {
    @POST("NEAR/signup/community")
    suspend fun signUp(
        @Body request: SignUpCommunityRequest
    ): Response<Void>

    @POST("NEAR/login/community")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("NEAR/community/me")
    suspend fun getCommunityInfo(
        @Header("Authorization") token: String
    ): Response<CommunityResponse>

    @POST("")
    suspend fun sendFcmToken(
        @Header("Authorization") header: String,
        @Body token: FcmTokenRequest
    ): Response<Void>

}