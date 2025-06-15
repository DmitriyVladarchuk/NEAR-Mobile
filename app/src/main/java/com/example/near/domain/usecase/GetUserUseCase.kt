package com.example.near.domain.usecase

import com.example.near.domain.models.user.User
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUserInfo().getOrNull()
    }
}