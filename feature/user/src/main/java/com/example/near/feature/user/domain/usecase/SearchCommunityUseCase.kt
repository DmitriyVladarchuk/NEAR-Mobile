package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.models.CommunitiesList
import com.example.near.feature.user.domain.repository.UserRepository

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