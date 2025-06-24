package com.example.near.community.usecase

import com.example.near.community.models.CommunityUpdateParams
import com.example.near.domain.community.repository.CommunityRepository

class UpdateCommunityUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityUpdateParams: CommunityUpdateParams): Result<Unit> {
        return communityRepository.updateCommunity(communityUpdateParams)
    }
}