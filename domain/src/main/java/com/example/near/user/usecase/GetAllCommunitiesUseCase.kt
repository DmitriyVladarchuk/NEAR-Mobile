package com.example.near.user.usecase

import com.example.near.domain.user.repository.UserRepository
import com.example.near.user.models.CommunitiesList

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