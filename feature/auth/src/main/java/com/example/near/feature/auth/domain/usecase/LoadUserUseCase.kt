package com.example.near.feature.auth.domain.usecase

import com.example.near.common.storage.AuthDataStorage
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.feature.auth.domain.model.AuthCheckResult
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.repository.CommunityAuthRepository
import com.example.near.feature.auth.domain.repository.UserAuthRepository

class LoadUserUseCase(
    private val userRepository: UserAuthRepository,
    private val communityRepository: CommunityAuthRepository,
    private val authDataStorage: AuthDataStorage,
    private val emailVerificationStorage: EmailVerificationStorage,
    private val loginUserUseCase: LoginUserUseCase,
    private val loginCommunityUseCase: LoginCommunityUseCase
) {
    suspend operator fun invoke(): AuthCheckResult {
        return try {
            val credentials = authDataStorage.getCredentials()

            if (credentials == null) {
                val pendingEmail = emailVerificationStorage.getPendingEmail()
                    ?: return AuthCheckResult.NotAuthenticated

                if (pendingEmail.isCommunity) {
                    loginCommunityUseCase(
                        email = pendingEmail.email,
                        password = pendingEmail.password
                    ).fold(
                        onSuccess = { status ->
                            if (status is EmailVerificationStatus.Verified) AuthCheckResult.Authenticated(true)
                            else {
                                AuthCheckResult.EmailNotVerified
                            }
                        },
                        onFailure = { AuthCheckResult.NotAuthenticated }
                    )
                } else {
                    loginUserUseCase(
                        email = pendingEmail.email,
                        password = pendingEmail.password
                    ).fold(
                        onSuccess = { status ->
                            if (status is EmailVerificationStatus.Verified) AuthCheckResult.Authenticated(false)
                            else AuthCheckResult.EmailNotVerified
                        },
                        onFailure = { AuthCheckResult.NotAuthenticated }
                    )
                }
            } else {
                emailVerificationStorage.clearPendingEmail()
                val result = if (credentials.isCommunity) {
                    communityRepository.refreshToken()
                } else {
                    userRepository.refreshToken()
                }

                result.fold(
                    onSuccess = { AuthCheckResult.Authenticated(credentials.isCommunity) },
                    onFailure = { AuthCheckResult.Error(it.toString()) }
                )
            }

        } catch (e: Exception) {
            AuthCheckResult.Error(e.toString())
        }
    }
}