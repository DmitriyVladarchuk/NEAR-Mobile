package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.repository.UserRepository
import com.example.near.feature.community.domain.model.Community

class GetCommunityByIdUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Community? {
        return userRepository.getCommunityById(id).getOrNull()
    }
}