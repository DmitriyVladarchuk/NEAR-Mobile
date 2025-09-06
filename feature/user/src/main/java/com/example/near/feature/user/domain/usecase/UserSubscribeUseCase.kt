package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.repository.UserRepository

class UserSubscribeUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return userRepository.userSubscribe(id)
    }
}