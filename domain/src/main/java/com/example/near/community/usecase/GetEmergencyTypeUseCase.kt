package com.example.near.community.usecase

import com.example.near.common.models.EmergencyType
import com.example.near.common.models.emergencyTypes
import com.example.near.domain.community.repository.CommunityRepository

class GetEmergencyTypeUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Result<List<EmergencyType>> {
        return Result.success(emergencyTypes)
    }
}