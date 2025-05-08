package com.example.near.data.datastore

import com.example.near.data.models.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private var _authToken: LoginResponse? = null

    val authToken: LoginResponse?
        get() = _authToken

    fun saveAuthToken(authToken: LoginResponse) {
        _authToken = authToken
    }

    fun clearAuthToken() {
        _authToken = null
    }

    val isLoggedIn: Boolean
        get() = _authToken != null
}