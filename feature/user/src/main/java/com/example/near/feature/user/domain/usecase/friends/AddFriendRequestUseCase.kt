package com.example.near.domain.user.usecase.friends

import com.example.near.feature.user.domain.repository.UserRepository

class AddFriendRequestUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.addFriendRequest(friendId)
    }
}