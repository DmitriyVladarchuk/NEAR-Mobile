package com.example.near.domain.user.usecase.friends

import com.example.near.domain.user.repository.UserRepository

class SendFriendRequestUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.sendFriendRequest(friendId)
    }
}