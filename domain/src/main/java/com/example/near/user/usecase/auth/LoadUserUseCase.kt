package com.example.near.domain.user.usecase.auth

import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.user.repository.UserRepository

class LoadUserUseCase(
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
    private val authDataStorage: AuthDataStorage,
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val credentials = authDataStorage.getCredentials() ?:
            throw IllegalStateException("No credentials found")

            val result = if (credentials.isCommunity) {
                communityRepository.refreshToken()
            } else {
                userRepository.refreshToken()
            }

            result.fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}