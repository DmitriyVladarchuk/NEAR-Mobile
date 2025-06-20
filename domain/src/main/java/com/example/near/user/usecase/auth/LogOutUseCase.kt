package com.example.near.domain.user.usecase.auth

import com.example.near.domain.shared.storage.AuthDataStorage

class LogOutUseCase(
    //private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage
) {
    operator fun invoke() {
        authDataStorage.clearCredentials()
        //sessionManager.clearAuthToken()
    }
}