package com.example.near.domain.shared.usecase

import com.example.near.domain.user.models.User
import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): User? {
        return userRepository.getUserById(id).getOrNull()
    }
}