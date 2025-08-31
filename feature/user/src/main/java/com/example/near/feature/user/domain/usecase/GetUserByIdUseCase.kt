package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.models.User
import com.example.near.feature.user.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): User? {
        return userRepository.getUserById(id).getOrNull()
    }
}