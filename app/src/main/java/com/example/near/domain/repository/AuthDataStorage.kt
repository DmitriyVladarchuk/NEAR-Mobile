package com.example.near.domain.repository

import com.example.near.domain.models.common.AuthCredentials

interface AuthDataStorage {
    fun getCredentials(): AuthCredentials?
    fun saveCredentials(credentials: AuthCredentials)
    fun clearCredentials()

    fun getFcmToken(): String?
    fun saveFcmToken(token: String)
    fun saveIsPush()
    fun clearFcmToken()
}