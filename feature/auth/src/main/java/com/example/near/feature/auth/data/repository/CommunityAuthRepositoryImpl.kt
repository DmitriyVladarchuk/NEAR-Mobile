package com.example.near.feature.auth.data.repository

import com.example.near.common.models.LoginCredentials
import com.example.near.common.storage.AuthDataStorage
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.core.network.model.RefreshTokenRequest
import com.example.near.core.network.service.CommunityService
import com.example.near.data.storage.SessionManager
import com.example.near.feature.auth.data.mapper.toDomain
import com.example.near.feature.auth.data.mapper.toRequest
import com.example.near.feature.auth.domain.model.CommunitySignup
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.repository.CommunityAuthRepository

class CommunityAuthRepositoryImpl(
    private val communityService: CommunityService,
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage,
    private val emailVerificationStorage: EmailVerificationStorage
) : CommunityAuthRepository {
    override suspend fun signUp(
        communitySignup: CommunitySignup
    ): Result<EmailVerificationStatus> {
        return try {
            val request = communitySignup.toRequest()
            val response = communityService.signUp(request)

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
                emailVerificationStorage.savePendingEmail(credentials.email, credentials.password, false)
                Result.success(EmailVerificationStatus.NotVerified)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
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
                Result.failure(Exception("Refresh failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}