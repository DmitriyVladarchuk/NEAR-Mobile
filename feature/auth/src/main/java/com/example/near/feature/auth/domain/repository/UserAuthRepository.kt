package com.example.near.feature.auth.domain.repository

import com.example.near.common.models.LoginCredentials
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.model.UserSignUp

interface UserAuthRepository {
    suspend fun signUp(userSignUp: UserSignUp): Result<EmailVerificationStatus>
    suspend fun login(credentials: LoginCredentials): Result<EmailVerificationStatus>
    suspend fun refreshToken(): Result<Unit>
}