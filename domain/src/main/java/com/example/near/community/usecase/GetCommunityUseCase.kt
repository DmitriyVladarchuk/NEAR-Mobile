package com.example.near.domain.community.usecase

import com.example.near.domain.community.models.Community
import com.example.near.domain.community.repository.CommunityRepository

class GetCommunityUseCase(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Community {
        return communityRepository.getCommunityInfo().getOrThrow()
    }
}