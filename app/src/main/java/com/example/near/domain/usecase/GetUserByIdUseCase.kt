package com.example.near.domain.usecase

import com.example.near.domain.models.User
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String): User? {
        return userRepository.getUserById(id).getOrNull()
    }
}