package com.example.near.domain.usecase.user.auth

import android.util.Log
import com.example.near.domain.models.common.AuthCredentials
import com.example.near.domain.models.common.AuthTokens
import com.example.near.domain.models.common.LoginCredentials
import com.example.near.domain.repository.AuthDataStorage
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authDataStorage: AuthDataStorage,
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthTokens> {
        return userRepository.login(LoginCredentials(email, password)).also { result ->
            if (result.isSuccess) {
                val tokens = result.getOrThrow()
                Log.d("tokens", tokens.toString())
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