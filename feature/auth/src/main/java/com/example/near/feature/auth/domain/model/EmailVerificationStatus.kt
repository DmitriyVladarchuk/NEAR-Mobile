package com.example.near.feature.auth.domain.model

import com.example.near.core.network.AuthTokens

sealed class EmailVerificationStatus {
    object NotAuth : EmailVerificationStatus()
    object NotVerified : EmailVerificationStatus()
    data class Verified(val authTokens: AuthTokens) : EmailVerificationStatus()
    data class Error(val exception: Exception) : EmailVerificationStatus()
}