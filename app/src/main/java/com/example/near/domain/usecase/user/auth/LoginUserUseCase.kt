package com.example.near.domain.usecase.user.auth

import com.example.near.data.storage.AuthDataStorage
import com.example.near.data.storage.SessionManager
import com.example.near.domain.models.common.AuthTokens
import com.example.near.domain.models.user.LoginCredentials
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authDataStorage: AuthDataStorage,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthTokens> {
        return userRepository.login(LoginCredentials(email, password)).also { result ->
            if (result.isSuccess) {
                val tokens = result.getOrThrow()
                authDataStorage.saveCredentials(tokens.refreshToken, false)
                sessionManager.saveAuthToken(tokens)

                // Отправка токена
                authDataStorage.getFcmToken()?.let { token ->
                    userRepository.sendFcmToken(token).onSuccess {
                        authDataStorage.clearFcmToken()
                    }
                }
            }
        }
    }
}