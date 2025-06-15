package com.example.near.domain.usecase.community

import com.example.near.domain.models.community.Community
import com.example.near.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Community {
        return communityRepository.getCommunityInfo().getOrThrow()
    }
}