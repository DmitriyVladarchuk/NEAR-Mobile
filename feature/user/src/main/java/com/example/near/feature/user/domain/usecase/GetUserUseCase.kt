package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.models.User
import com.example.near.feature.user.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUserInfo().getOrNull()
    }
}