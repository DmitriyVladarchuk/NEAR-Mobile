package com.example.near.domain.shared.usecase

import com.example.near.domain.user.models.User
import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUserInfo().getOrNull()
    }
}