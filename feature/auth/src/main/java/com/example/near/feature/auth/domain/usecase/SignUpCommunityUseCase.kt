package com.example.near.feature.auth.domain.usecase

import com.example.near.feature.auth.domain.storage.EmailVerificationStorage
import com.example.near.feature.auth.domain.model.CommunitySignup
import com.example.near.feature.auth.domain.model.EmailVerificationStatus
import com.example.near.feature.auth.domain.repository.CommunityAuthRepository

class SignUpCommunityUseCase(
    private val communityRepository: CommunityAuthRepository,
    private val emailVerificationStorage: EmailVerificationStorage,
) {
    suspend operator fun invoke(
        communitySignup: CommunitySignup
    ): Result<EmailVerificationStatus> {

        val result = communityRepository.signUp(communitySignup)
        if (result.isSuccess) {
            if (result.getOrThrow() is EmailVerificationStatus.NotVerified) {
                emailVerificationStorage.savePendingEmail(
                    email = communitySignup.email,
                    password = communitySignup.password,
                    isCommunity = true
                )
            }
        }
        return result
    }
}