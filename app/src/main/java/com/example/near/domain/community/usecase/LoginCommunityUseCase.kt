package com.example.near.domain.community.usecase

import com.example.near.domain.shared.models.AuthCredentials
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.community.repository.CommunityRepository
import javax.inject.Inject

class LoginCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val authDataStorage: AuthDataStorage,
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthTokens> {
        return communityRepository.login(email, password).also { result ->
            if (result.isSuccess) {
                authDataStorage.saveCredentials(
                    AuthCredentials(
                        result.getOrThrow().accessToken,
                        result.getOrThrow().refreshToken!!,
                        true
                    )
                )

                // Отправка токена
                authDataStorage.getFcmToken()?.let { token ->
                    communityRepository.sendFcmToken(token).onSuccess {
                        authDataStorage.clearFcmToken()
                    }
                }
            }
        }
    }
}