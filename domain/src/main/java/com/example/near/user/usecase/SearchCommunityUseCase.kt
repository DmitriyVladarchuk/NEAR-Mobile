package com.example.near.user.usecase

import com.example.near.domain.user.repository.UserRepository
import com.example.near.user.models.CommunitiesList

class SearchCommunityUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(value: String): Result<CommunitiesList> {
        return try {
            userRepository.searchCommunityByValue(value = value)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to search communities: ${e.message}"))
        }
    }
}