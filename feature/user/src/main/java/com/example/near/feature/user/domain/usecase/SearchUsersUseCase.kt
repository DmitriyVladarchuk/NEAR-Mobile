package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.models.UserList
import com.example.near.feature.user.domain.repository.UserRepository

class SearchUsersUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(value: String): Result<UserList> {
        return try {
            userRepository.searchUsersByValue(value = value)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to search communities: ${e.message}"))
        }
    }
}