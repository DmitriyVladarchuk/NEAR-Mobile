package com.example.near.domain.user.usecase.friends

import com.example.near.domain.user.repository.UserRepository

class RemoveFriendUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        val result = userRepository.removeFriend(friendId)
        return result
    }
}