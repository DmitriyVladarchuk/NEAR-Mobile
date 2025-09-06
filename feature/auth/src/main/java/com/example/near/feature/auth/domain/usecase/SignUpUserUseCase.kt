package com.example.near.feature.auth.domain.usecase

import com.example.near.feature.auth.domain.storage.EmailVerificationStorage
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.model.UserSignUp
import com.example.near.feature.auth.domain.repository.UserAuthRepository

class SignUpUserUseCase(
    private val userRepository: UserAuthRepository,
    private val emailVerificationStorage: EmailVerificationStorage,
) {
    suspend operator fun invoke(userSignUp: UserSignUp): Result<EmailVerificationStatus> {
        val result = userRepository.signUp(userSignUp)
        if (result.isSuccess) {
            if (result.getOrThrow() is EmailVerificationStatus.NotVerified) {
                emailVerificationStorage.savePendingEmail(
                    email = userSignUp.email,
                    password = userSignUp.password,
                    isCommunity = false
                )
            }
        }
        return result
    }
}