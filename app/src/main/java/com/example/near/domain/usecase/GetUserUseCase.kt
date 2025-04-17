package com.example.near.domain.usecase

import com.example.near.data.datastore.SessionManager
import com.example.near.domain.models.User
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUserInfo(sessionManager.authToken!!.accessToken).getOrNull()
    }
}