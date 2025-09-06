package com.example.near.feature.auth.data.repository

import com.example.near.feature.auth.domain.model.LoginCredentials
import com.example.near.feature.auth.domain.storage.AuthDataStorage
import com.example.near.feature.auth.domain.storage.EmailVerificationStorage
import com.example.near.core.network.model.RefreshTokenRequest
import com.example.near.core.network.service.UserService
import com.example.near.core.network.SessionManager
import com.example.near.feature.auth.data.mapper.toDomain
import com.example.near.feature.auth.data.mapper.toRequest
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.model.UserSignUp
import com.example.near.feature.auth.domain.repository.UserAuthRepository

class UserAuthRepositoryImpl(
    private val userService: UserService,
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage,
    private val emailVerificationStorage: EmailVerificationStorage
) : UserAuthRepository {

    override suspend fun signUp(
        userSignUp: UserSignUp
    ): Result<EmailVerificationStatus> {
        return try {
            val request = userSignUp.toRequest()
            val response = userService.signUp(request)

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
            val response = userService.login(credentials.toRequest())
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    sessionManager.saveAuthToken(it)
                    sessionManager.setCommunityFlag(false)
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
            val response = userService.refreshToken(
                token = "Bearer ${tokens?.accessToken}",
                request = RefreshTokenRequest(tokens?.refreshToken ?: "")
            )

            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    sessionManager.saveAuthToken(it)
                    sessionManager.setCommunityFlag(false)
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