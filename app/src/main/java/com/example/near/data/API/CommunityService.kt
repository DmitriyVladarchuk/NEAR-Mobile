package com.example.near.data.API

import com.example.near.data.models.FcmTokenRequest
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.TemplateCreateRequest
import com.example.near.data.models.community.CommunityResponse
import com.example.near.data.models.community.SignUpCommunityRequest
import com.example.near.domain.models.TemplateSend
import com.example.near.domain.models.UserTemplate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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

    // --- Template actions ---

    @POST("NEAR/community/template/create")
    suspend fun createTemplate(
        @Header("Authorization") token: String,
        @Body request: TemplateCreateRequest
    ): Response<Void>

    @PUT("NEAR/community/template/update")
    suspend fun updateTemplate(
        @Header("Authorization") token: String,
        @Body request: UserTemplate
    ): Response<Void>

    @HTTP(method = "DELETE", path = "NEAR/community/template/delete", hasBody = true)
    suspend fun deleteTemplate(
        @Header("Authorization") token: String,
        @Body request: UserTemplate
    ): Response<Void>

    @POST("NEAR/community/template/send")
    suspend fun sendTemplate(
        @Header("Authorization") token: String,
        @Body request: TemplateSend
    ): Response<Void>

}