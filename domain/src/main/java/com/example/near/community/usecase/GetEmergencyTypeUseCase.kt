package com.example.near.community.usecase

import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.models.EmergencyType

class GetEmergencyTypeUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Result<List<EmergencyType>> {
        return communityRepository.getEmergencyType()
    }
}