package com.example.near.feature.auth.domain.repository

import com.example.near.common.models.LoginCredentials
import com.example.near.feature.auth.domain.model.CommunitySignup
import com.example.near.feature.auth.domain.model.EmailVerificationStatus

interface CommunityAuthRepository {
    suspend fun signUp(communitySignup: CommunitySignup): Result<EmailVerificationStatus>
    suspend fun login(credentials: LoginCredentials): Result<EmailVerificationStatus>
    suspend fun refreshToken(): Result<Unit>
}