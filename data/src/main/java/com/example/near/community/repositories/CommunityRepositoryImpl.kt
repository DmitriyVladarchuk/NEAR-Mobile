package com.example.near.data.community.repositories

import android.util.Log
import com.example.near.common.models.EmailVerificationStatus
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.community.models.CommunityUpdateParams
import com.example.near.data.api.CommunityService
import com.example.near.data.community.mappers.toDomain
import com.example.near.data.community.mappers.toRequest
import com.example.near.data.community.models.SignUpCommunityRequest
import com.example.near.data.shared.models.FcmTokenRequest
import com.example.near.data.shared.models.LoginRequest
import com.example.near.data.shared.models.RefreshTokenRequest
import com.example.near.data.shared.models.TemplateCreateRequest
import com.example.near.data.shared.models.TemplateSendRequest
import com.example.near.data.storage.SessionManager
import com.example.near.data.user.mappers.toDomain
import com.example.near.data.user.mappers.toRequest
import com.example.near.domain.community.models.Community
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.models.emergencyTypes
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.user.models.UserTemplate


class CommunityRepositoryImpl(
    private val communityService: CommunityService,
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage,
    private val emailVerificationStorage: EmailVerificationStorage
) : CommunityRepository {

    override suspend fun signUp(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<EmailVerificationStatus> {
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
            Log.d("ComRep",
                SignUpCommunityRequest(
                    communityName,
                    email,
                    password,
                    location,
                    monitoredEmergencyTypes
                ).toString())
            Log.d("ComRep", response.code().toString())
            if (response.isSuccessful) {
                Result.success(EmailVerificationStatus.NotVerified)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("SignUp error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(credentials: LoginCredentials): Result<EmailVerificationStatus> {
        return try {
            val response = communityService.login(credentials.toRequest())
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    sessionManager.saveAuthToken(it)
                    Result.success(EmailVerificationStatus.Verified(it))
                } ?: Result.failure(Exception("Empty response body"))
            } else if(response.code() == 403) {
                emailVerificationStorage.savePendingEmail(credentials.email, credentials.password, true)
                Result.success(EmailVerificationStatus.NotVerified)
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

    override suspend fun updateCommunity(communityUpdateParams: CommunityUpdateParams): Result<Unit> {
        return try {
            val response = communityService.updateCommunity(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = communityUpdateParams.toRequest()
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to update community profile request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(): Result<Unit> {
        return try {
            val tokens = authDataStorage.getCredentials()
            val response = communityService.refreshToken(
                token = "Bearer ${tokens?.accessToken}",
                request = RefreshTokenRequest(tokens?.refreshToken ?: "")
            )
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

    override suspend fun getEmergencyType(): Result<List<EmergencyType>> {
        return try {
            val response = communityService.getEmergencyType("Bearer ${sessionManager.authToken!!.accessToken}")
            if (response.isSuccessful) {
                val emergencyTypes = response.body()?.map { it.toDomain() }
                Result.success(emergencyTypes ?: emptyList())
            } else {
                Result.failure(Exception("Failed getEmergencyType request"))
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