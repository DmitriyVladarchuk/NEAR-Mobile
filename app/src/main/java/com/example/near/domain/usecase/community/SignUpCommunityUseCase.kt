package com.example.near.domain.usecase.community

import com.example.near.domain.models.EmergencyType
import com.example.near.domain.repository.CommunityRepository
import javax.inject.Inject

class SignUpCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val loginCommunityUseCase: LoginCommunityUseCase
) {
    suspend operator fun invoke(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<Unit> {
        val result = communityRepository.signUp(communityName, email, password, location, monitoredEmergencyTypes)
        if (result.isSuccess) {
            loginCommunityUseCase(email, password)
        }
        return result
    }
}