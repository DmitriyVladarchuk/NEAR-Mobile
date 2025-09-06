package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.repository.UserRepository

class UserCancelSubscribeUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return userRepository.userCancelSubscribe(id)
    }
}