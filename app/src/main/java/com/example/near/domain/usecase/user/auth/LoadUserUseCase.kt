package com.example.near.domain.usecase.user.auth

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.models.LoginResponse
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
    private val authDataStorage: AuthDataStorage,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Boolean {
        val credentials = authDataStorage.getCredentials() ?: return false

        return try {
            val result = if (credentials.second) {
                communityRepository.refreshToken(credentials.first)
            } else {
                userRepository.refreshToken(credentials.first)
            }

            if (result.isSuccess) {
                result.getOrNull()?.let { token ->
                    sessionManager.saveAuthToken(
                        LoginResponse(
                            type = token.type,
                            accessToken = token.accessToken,
                            refreshToken = credentials.first,
                            uuid = null
                        )
                    )
                    true
                } ?: false
            } else {
                authDataStorage.clearCredentials()
                false
            }
        } catch (e: Exception) {
            authDataStorage.clearCredentials()
            false
        }
    }
}