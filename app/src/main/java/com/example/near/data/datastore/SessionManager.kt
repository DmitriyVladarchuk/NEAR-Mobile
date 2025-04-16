package com.example.near.data.datastore

import com.example.near.data.models.LoginUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private var _authToken: LoginUserResponse? = null

    val authToken: LoginUserResponse?
        get() = _authToken

    fun saveAuthToken(authToken: LoginUserResponse) {
        _authToken = authToken
    }

    fun clearAuthToken() {
        _authToken = null
    }

    val isLoggedIn: Boolean
        get() = _authToken != null
}