package com.example.near.user.usecase

import com.example.near.domain.user.repository.UserRepository
import com.example.near.user.models.UserList

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