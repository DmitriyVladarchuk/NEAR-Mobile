package com.example.near.feature.auth.domain.storage

import com.example.near.feature.auth.domain.model.AuthCredentials


interface AuthDataStorage {
    fun getCredentials(): AuthCredentials?
    fun saveCredentials(credentials: AuthCredentials)
    fun clearCredentials()

    fun getFcmToken(): String?
    fun saveFcmToken(token: String)
    fun saveIsPush()
    fun clearFcmToken()
}