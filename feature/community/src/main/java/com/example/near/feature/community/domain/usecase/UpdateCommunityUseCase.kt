package com.example.near.feature.community.domain.usecase

import com.example.near.feature.community.domain.model.CommunityUpdateParams
import com.example.near.feature.community.domain.repository.CommunityRepository

class UpdateCommunityUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityUpdateParams: CommunityUpdateParams): Result<Unit> {
        return communityRepository.updateCommunity(communityUpdateParams)
    }
}