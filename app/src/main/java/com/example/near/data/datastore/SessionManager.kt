package com.example.near.data.datastore

import com.example.near.domain.models.common.AuthTokens
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private var _authToken: AuthTokens? = null

    val authToken: AuthTokens?
        get() = _authToken

    fun saveAuthToken(authToken: AuthTokens) {
        _authToken = authToken
    }

    fun clearAuthToken() {
        _authToken = null
    }

    val isLoggedIn: Boolean
        get() = _authToken != null
}