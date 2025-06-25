package com.example.near.common.models

import com.example.near.domain.shared.models.AuthTokens

sealed class EmailVerificationStatus {
    object NotAuth : EmailVerificationStatus()
    object NotVerified : EmailVerificationStatus()
    data class Verified(val authTokens: AuthTokens) : EmailVerificationStatus()
    data class Error(val exception: Exception) : EmailVerificationStatus()
}