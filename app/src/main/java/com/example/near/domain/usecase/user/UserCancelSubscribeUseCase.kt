package com.example.near.domain.usecase.user

import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class UserCancelSubscribeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return userRepository.userCancelSubscribe(id)
    }
}