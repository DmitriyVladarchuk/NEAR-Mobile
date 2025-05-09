package com.example.near.domain.usecase.community

import com.example.near.data.models.community.CommunityResponse
import com.example.near.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): CommunityResponse {
        return communityRepository.getCommunityInfo().getOrThrow()
    }
}