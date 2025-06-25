package com.example.near.domain.user.usecase.auth

import com.example.near.common.models.AuthCheckResult
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.user.repository.UserRepository

//sealed class AuthCheckResult {
//    object NotAuthenticated : AuthCheckResult()
//    object EmailNotVerified : AuthCheckResult()
//    data class Authenticated(val isCommunity: Boolean) : AuthCheckResult()
//    data class Error(val exception: String) : AuthCheckResult()
//}


class LoadUserUseCase(
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
    private val authDataStorage: AuthDataStorage,
    private val emailVerificationStorage: EmailVerificationStorage
) {
    suspend operator fun invoke(): AuthCheckResult {
        return try {
            val credentials = authDataStorage.getCredentials()

            if (credentials == null) {
                val pendingEmail = emailVerificationStorage.getPendingEmail()
                    ?: return AuthCheckResult.NotAuthenticated

                if (pendingEmail.isCommunity) {
                    communityRepository.login(
                        email = pendingEmail.email,
                        password = pendingEmail.password
                    ).fold(
                        onSuccess = { AuthCheckResult.Authenticated(true) },
                        onFailure = { AuthCheckResult.EmailNotVerified }
                    )
                } else {
                    userRepository.login(LoginCredentials(
                        email = pendingEmail.email,
                        password = pendingEmail.password,
                        isCommunity = false
                    )).fold(
                        onSuccess = { AuthCheckResult.Authenticated(false) },
                        onFailure = { AuthCheckResult.EmailNotVerified }
                    )
                }
            } else {
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