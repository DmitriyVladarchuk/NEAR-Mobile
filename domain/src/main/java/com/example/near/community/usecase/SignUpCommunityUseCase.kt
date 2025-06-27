package com.example.near.domain.community.usecase

import com.example.near.common.models.EmailVerificationStatus
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.models.EmergencyType

class SignUpCommunityUseCase(
    private val communityRepository: CommunityRepository,
    private val emailVerificationStorage: EmailVerificationStorage,
) {
    suspend operator fun invoke(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<EmailVerificationStatus> {

        val result = communityRepository.signUp(communityName, email, password, location, monitoredEmergencyTypes)
        if (result.isSuccess) {
            if (result.getOrThrow() is EmailVerificationStatus.NotVerified) {
                emailVerificationStorage.savePendingEmail(
                    email = email,
                    password = password,
                    isCommunity = true
                )
            }
        }
        return result
    }
}