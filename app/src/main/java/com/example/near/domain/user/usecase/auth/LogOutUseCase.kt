package com.example.near.domain.user.usecase.auth

import com.example.near.data.storage.SessionManager
import com.example.near.domain.shared.storage.AuthDataStorage
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