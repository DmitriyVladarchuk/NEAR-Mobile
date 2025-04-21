package com.example.near.domain.usecase

import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class AddFriendRequestUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.addFriendRequest(friendId)
    }
}