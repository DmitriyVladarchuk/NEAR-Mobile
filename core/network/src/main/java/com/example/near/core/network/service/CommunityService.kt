package com.example.near.core.network.service

import com.example.near.core.network.model.commmunity.UpdateCommunityRequest
import com.example.near.core.network.model.FcmTokenRequest
import com.example.near.core.network.model.RefreshTokenRequest
import com.example.near.core.network.model.TemplateCreateRequest
import com.example.near.core.network.model.commmunity.CommunityResponse
import com.example.near.core.network.model.commmunity.SignUpCommunityRequest
import com.example.near.core.network.model.EmergencyTypeResponse
import com.example.near.core.network.model.LoginRequest
import com.example.near.core.network.model.LoginResponse
import com.example.near.core.network.model.TemplateSendRequest
import com.example.near.user.models.UserTemplate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
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

    @PATCH("NEAR/community/update")
    suspend fun updateCommunity(
        @Header("Authorization") token: String,
        @Body request: UpdateCommunityRequest
    ): Response<Void>

    @POST("NEAR/token/community")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
        @Body request: RefreshTokenRequest
    ): Response<LoginResponse>

    @GET("NEAR/community/get-emergency-type")
    suspend fun getEmergencyType(
        @Header("Authorization") token: String,
    ): Response<List<EmergencyTypeResponse>>

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
        @Body request: TemplateSendRequest
    ): Response<Void>

}