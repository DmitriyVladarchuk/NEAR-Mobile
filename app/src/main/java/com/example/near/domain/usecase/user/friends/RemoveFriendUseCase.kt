package com.example.near.domain.usecase.user.friends

import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class RemoveFriendUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.removeFriend(friendId)
    }
}