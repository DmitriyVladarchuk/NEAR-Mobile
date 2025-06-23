package com.example.near.domain.shared.usecase

import com.example.near.domain.user.models.User
import com.example.near.domain.user.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUserInfo().getOrNull()
    }
}