package com.example.near.domain.usecase.user.auth

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.models.LoginUserResponse
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authDataStorage: AuthDataStorage,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginUserResponse> {
        return userRepository.login(email, password).also { result ->
            if (result.isSuccess) {
                authDataStorage.saveCredentials(email, password, false)
                sessionManager.saveAuthToken(result.getOrNull()!!)
            }
        }
    }
}