package com.example.near.core.network

class SessionManager {
    private var _authToken: AuthTokens? = null
    private var _isCommunity: Boolean = false

    val authToken: AuthTokens?
        get() = _authToken

    val isCommunity: Boolean
        get() = _isCommunity

    fun setCommunityFlag(isCommunity: Boolean) {
        _isCommunity = isCommunity
    }

    fun saveAuthToken(authToken: AuthTokens) {
        _authToken = authToken
    }

    fun clearAuthToken() {
        _authToken = null
    }

    val isLoggedIn: Boolean
        get() = _authToken != null
}