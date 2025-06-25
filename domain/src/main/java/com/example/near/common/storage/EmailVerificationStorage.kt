package com.example.near.common.storage

import com.example.near.domain.shared.models.LoginCredentials

interface EmailVerificationStorage {
    suspend fun savePendingEmail(email: String, password: String, isCommunity: Boolean)
    suspend fun getPendingEmail(): LoginCredentials?
    suspend fun clearPendingEmail()
}