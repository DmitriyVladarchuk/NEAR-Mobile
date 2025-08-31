package com.example.near.feature.community.domain.usecase

import com.example.near.core.network.model.EmergencyType
import com.example.near.core.network.model.emergencyTypes
import com.example.near.feature.community.domain.repository.CommunityRepository

class GetEmergencyTypeUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Result<List<EmergencyType>> {
        return Result.success(emergencyTypes)
    }
}