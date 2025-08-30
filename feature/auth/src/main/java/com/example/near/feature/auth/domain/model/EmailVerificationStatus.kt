package com.example.near.feature.auth.domain.model

import com.example.near.common.models.AuthTokens

sealed class EmailVerificationStatus {
    object NotAuth : EmailVerificationStatus()
    object NotVerified : EmailVerificationStatus()
    data class Verified(val authTokens: AuthTokens) : EmailVerificationStatus()
    data class Error(val exception: Exception) : EmailVerificationStatus()
}