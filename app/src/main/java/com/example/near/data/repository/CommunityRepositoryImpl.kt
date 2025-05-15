package com.example.near.data.repository

import android.util.Log
import com.example.near.data.API.CommunityService
import com.example.near.data.datastore.SessionManager
import com.example.near.data.models.FcmTokenRequest
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.TemplateCreateRequest
import com.example.near.data.models.community.CommunityResponse
import com.example.near.data.models.community.SignUpCommunityRequest
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.models.TemplateSend
import com.example.near.domain.models.UserTemplate
import com.example.near.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityService,
    private val sessionManager: SessionManager
) : CommunityRepository {

    override suspend fun signUp(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<Unit> {
        return try {
            val response = communityService.signUp(
                SignUpCommunityRequest(
                    communityName,
                    email,
                    password,
                    location,
                    monitoredEmergencyTypes
                )
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = communityService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommunityInfo(): Result<CommunityResponse> {
        return try {
            val response = communityService.getCommunityInfo("Bearer ${sessionManager.authToken!!.accessToken}")
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendFcmToken(token: String): Result<Unit> {
        return try {
            val response = communityService.sendFcmToken("Bearer ${sessionManager.authToken!!.accessToken}", FcmTokenRequest(token))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Template Actions ---

    override suspend fun createTemplate(
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return try {
            val response = communityService.createTemplate(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                TemplateCreateRequest(templateName, message, emergencyType)
            )
            Log.d("Test", response.message())
            Log.d("Test", response.code().toString())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Log.d("TestError", sessionManager.authToken!!.accessToken.toString())
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return try {
            val response = communityService.updateTemplate(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                UserTemplate(id, templateName, message, emergencyType)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return try {
            val response = communityService.deleteTemplate(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                UserTemplate(id, templateName, message, emergencyType)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendTemplate(templateId: String, recipients: List<String>): Result<Unit> {
        return try {
            val response = communityService.sendTemplate(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                TemplateSend(templateId, recipients)
            )
            Log.d("Test", response.message())
            Log.d("Test", response.code().toString())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Log.e("AAA", e.toString())
            Result.failure(e)
        }
    }

}