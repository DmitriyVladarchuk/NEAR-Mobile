package com.example.near.common.storage

import com.example.near.common.models.AuthCredentials


interface AuthDataStorage {
    fun getCredentials(): AuthCredentials?
    fun saveCredentials(credentials: AuthCredentials)
    fun clearCredentials()

    fun getFcmToken(): String?
    fun saveFcmToken(token: String)
    fun saveIsPush()
    fun clearFcmToken()
}