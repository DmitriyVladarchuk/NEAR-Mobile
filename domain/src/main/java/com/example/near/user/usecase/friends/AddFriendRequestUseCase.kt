package com.example.near.domain.user.usecase.friends

import com.example.near.domain.user.repository.UserRepository

class AddFriendRequestUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.addFriendRequest(friendId)
    }
}