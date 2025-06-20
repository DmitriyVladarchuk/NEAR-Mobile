package com.example.near.domain.user.usecase

import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class UserSubscribeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return userRepository.userSubscribe(id)
    }
}