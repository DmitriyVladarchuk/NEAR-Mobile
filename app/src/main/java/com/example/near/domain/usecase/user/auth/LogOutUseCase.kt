package com.example.near.domain.usecase.user.auth

import com.example.near.data.storage.AuthDataStorage
import com.example.near.data.storage.SessionManager
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage
) {
    operator fun invoke() {
        authDataStorage.clearCredentials()
        sessionManager.clearAuthToken()
    }
}