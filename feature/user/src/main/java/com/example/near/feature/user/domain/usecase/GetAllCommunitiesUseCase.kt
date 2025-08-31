package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.models.CommunitiesList
import com.example.near.feature.user.domain.repository.UserRepository

class GetAllCommunitiesUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<CommunitiesList> {
        return try {
            userRepository.getAllCommunities()
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get communities: ${e.message}"))
        }
    }
}