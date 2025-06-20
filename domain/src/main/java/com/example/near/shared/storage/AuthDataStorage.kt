package com.example.near.domain.shared.storage

import com.example.near.domain.shared.models.AuthCredentials

interface AuthDataStorage {
    fun getCredentials(): AuthCredentials?
    fun saveCredentials(credentials: AuthCredentials)
    fun clearCredentials()

    fun getFcmToken(): String?
    fun saveFcmToken(token: String)
    fun saveIsPush()
    fun clearFcmToken()
}