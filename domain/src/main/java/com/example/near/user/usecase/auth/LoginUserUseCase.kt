package com.example.near.domain.user.usecase.auth

import com.example.near.common.models.EmailVerificationStatus
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.domain.shared.models.AuthCredentials
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.user.repository.UserRepository

class LoginUserUseCase(
    private val userRepository: UserRepository,
    private val authDataStorage: AuthDataStorage,
) {
    suspend operator fun invoke(email: String, password: String): Result<EmailVerificationStatus> {

        val response = userRepository.login(LoginCredentials(email, password, false))
        return response
            .onSuccess { status ->
                if (status is EmailVerificationStatus.Verified) {
                    val tokens = status.authTokens
                    authDataStorage.saveCredentials(
                        AuthCredentials(
                            accessToken = tokens.accessToken,
                            refreshToken = tokens.refreshToken!!,
                            isCommunity = false
                        )
                    )
                }
            }
    }
}