package com.example.near.feature.auth.domain.storage

import com.example.near.feature.auth.domain.model.LoginCredentials

interface EmailVerificationStorage {
    suspend fun savePendingEmail(email: String, password: String, isCommunity: Boolean)
    suspend fun getPendingEmail(): LoginCredentials?
    suspend fun clearPendingEmail()
}