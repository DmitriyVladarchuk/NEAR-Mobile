package com.example.near.domain.user.usecase.friends

import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class RejectFriendRequestUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.rejectFriendRequest(friendId)
    }
}