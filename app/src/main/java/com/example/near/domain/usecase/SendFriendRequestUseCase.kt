package com.example.near.domain.usecase

import com.example.near.data.datastore.SessionManager
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        return userRepository.sendFriendRequest(sessionManager.authToken!!.accessToken, friendId)
    }
}