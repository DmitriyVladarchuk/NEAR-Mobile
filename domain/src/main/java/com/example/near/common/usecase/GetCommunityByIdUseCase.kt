package com.example.near.common.usecase

import com.example.near.domain.community.models.Community
import com.example.near.domain.user.repository.UserRepository

class GetCommunityByIdUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Community? {
        return userRepository.getCommunityById(id).getOrNull()
    }
}