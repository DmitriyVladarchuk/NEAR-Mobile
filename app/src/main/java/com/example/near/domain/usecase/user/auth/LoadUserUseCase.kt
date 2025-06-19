package com.example.near.domain.usecase.user.auth

import com.example.near.domain.repository.AuthDataStorage
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserUseCase @Inject constructor(
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