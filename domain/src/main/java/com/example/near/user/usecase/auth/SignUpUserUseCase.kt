package com.example.near.domain.user.usecase.auth

import com.example.near.common.models.EmailVerificationStatus
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.domain.shared.models.SignupNotificationOption
import com.example.near.domain.user.models.UserSignUp
import com.example.near.domain.user.repository.UserRepository

class SignUpUserUseCase(
    private val userRepository: UserRepository,
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