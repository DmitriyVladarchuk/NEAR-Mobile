package com.example.near.domain.community.usecase

import com.example.near.common.models.EmailVerificationStatus
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.models.AuthCredentials
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.storage.AuthDataStorage

class LoginCommunityUseCase(
    private val communityRepository: CommunityRepository,
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