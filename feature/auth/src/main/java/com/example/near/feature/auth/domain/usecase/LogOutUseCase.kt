package com.example.near.domain.user.usecase.auth

import com.example.near.common.storage.AuthDataStorage

class LogOutUseCase(
    private val authDataStorage: AuthDataStorage
) {
    operator fun invoke() {
        authDataStorage.clearCredentials()
    }
}