package com.example.near.feature.community.domain.usecase

import com.example.near.feature.community.domain.model.Community
import com.example.near.feature.community.domain.repository.CommunityRepository

class GetCommunityUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Community {
        return communityRepository.getCommunityInfo().getOrThrow()
    }
}