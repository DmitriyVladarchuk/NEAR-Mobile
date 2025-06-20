package com.example.near.domain.user.usecase

import com.example.near.domain.user.repository.UserRepository

class UserCancelSubscribeUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return userRepository.userCancelSubscribe(id)
    }
}