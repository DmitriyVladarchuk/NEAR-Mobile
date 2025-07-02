package com.example.near.domain.user.usecase.auth

import com.example.near.common.models.AuthCheckResult
import com.example.near.common.models.EmailVerificationStatus
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.community.usecase.LoginCommunityUseCase
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.user.repository.UserRepository


class LoadUserUseCase(
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
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