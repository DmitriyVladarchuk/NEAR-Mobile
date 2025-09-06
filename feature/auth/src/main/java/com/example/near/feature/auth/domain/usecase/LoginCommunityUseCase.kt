package com.example.near.feature.auth.domain.usecase

import com.example.near.feature.auth.domain.model.AuthCredentials
import com.example.near.feature.auth.domain.model.LoginCredentials
import com.example.near.feature.auth.domain.storage.AuthDataStorage
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.repository.CommunityAuthRepository

class LoginCommunityUseCase(
    private val communityRepository: CommunityAuthRepository,
    private val authDataStorage: AuthDataStorage,
) {
    suspend operator fun invoke(email: String, password: String): Result<EmailVerificationStatus> {

        val response = communityRepository.login(LoginCredentials(email, password, true))
        return response
            .onSuccess { status ->
                if (status is EmailVerificationStatus.Verified) {
                    val tokens = status.authTokens
                    authDataStorage.saveCredentials(
                        AuthCredentials(
                            accessToken = tokens.accessToken,
                            refreshToken = tokens.refreshToken!!,
                            isCommunity = true
                        )
                    )
                }
        }
    }
}