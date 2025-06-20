package com.example.near.domain.community.usecase

import com.example.near.domain.community.models.Community
import com.example.near.domain.community.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Community {
        return communityRepository.getCommunityInfo().getOrThrow()
    }
}