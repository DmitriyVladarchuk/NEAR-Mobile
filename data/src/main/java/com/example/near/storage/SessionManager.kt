package com.example.near.data.storage

import com.example.near.common.models.AuthTokens

class SessionManager {
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