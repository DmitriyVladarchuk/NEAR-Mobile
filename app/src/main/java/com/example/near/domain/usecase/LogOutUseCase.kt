package com.example.near.domain.usecase

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
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