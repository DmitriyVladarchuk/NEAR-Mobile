package com.example.near.data.community.repositories

import android.util.Log
import com.example.near.data.api.CommunityService
import com.example.near.data.community.mappers.toDomain
import com.example.near.data.community.models.SignUpCommunityRequest
import com.example.near.data.shared.models.FcmTokenRequest
import com.example.near.data.shared.models.LoginRequest
import com.example.near.data.shared.models.RefreshTokenRequest
import com.example.near.data.shared.models.TemplateCreateRequest
import com.example.near.data.shared.models.TemplateSendRequest
import com.example.near.data.storage.SessionManager
import com.example.near.data.user.mappers.toDomain
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.domain.community.models.Community
import com.example.near.domain.user.models.UserTemplate
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.community.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityService,
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage
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

    override suspend fun login(email: String, password: String): Result<AuthTokens> {
        return try {
            val response = communityService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    sessionManager.saveAuthToken(it)
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommunityInfo(): Result<Community> {
        return try {
            val response = communityService.getCommunityInfo("Bearer ${sessionManager.authToken!!.accessToken}")
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
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

    override suspend fun refreshToken(): Result<Unit> {
        return try {
            val tokens = authDataStorage.getCredentials()
            val response = communityService.refreshToken(RefreshTokenRequest(tokens?.refreshToken ?: ""))
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    sessionManager.saveAuthToken(it)
                    Result.success(Unit)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Failed to send token request"))
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
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
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
                TemplateSendRequest(templateId, recipients)
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