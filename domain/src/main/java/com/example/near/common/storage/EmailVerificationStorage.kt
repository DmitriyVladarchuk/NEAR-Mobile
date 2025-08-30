package com.example.near.common.storage

import com.example.near.common.models.LoginCredentials

interface EmailVerificationStorage {
    suspend fun savePendingEmail(email: String, password: String, isCommunity: Boolean)
    suspend fun getPendingEmail(): LoginCredentials?
    suspend fun clearPendingEmail()
}