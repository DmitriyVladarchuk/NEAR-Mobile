package com.example.near.domain.user.usecase.auth

import com.example.near.domain.shared.models.AuthCredentials
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.user.repository.UserRepository

class LoginUserUseCase(
    private val userRepository: UserRepository,
    private val authDataStorage: AuthDataStorage,
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthTokens> {
        return userRepository.login(LoginCredentials(email, password)).also { result ->
            if (result.isSuccess) {
                val tokens = result.getOrThrow()
                authDataStorage.saveCredentials(
                    AuthCredentials(
                        tokens.accessToken,
                        tokens.refreshToken!!,
                        false
                    )
                )

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