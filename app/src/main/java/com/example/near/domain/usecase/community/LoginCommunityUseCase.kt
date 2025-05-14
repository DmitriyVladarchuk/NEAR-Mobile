package com.example.near.domain.usecase.community

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.models.LoginResponse
import com.example.near.domain.repository.CommunityRepository
import javax.inject.Inject

class LoginCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val authDataStorage: AuthDataStorage,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginResponse> {
        return communityRepository.login(email, password).also { result ->
            if (result.isSuccess) {
                authDataStorage.saveCredentials(email, password, true)
                sessionManager.saveAuthToken(result.getOrNull()!!)

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