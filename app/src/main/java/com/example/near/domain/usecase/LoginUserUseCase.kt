package com.example.near.domain.usecase

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.models.LoginUserResponse
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authDataStorage: AuthDataStorage
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginUserResponse> {
        return userRepository.login(email, password).also { result ->
            if (result.isSuccess) {
                authDataStorage.saveCredentials(email, password)
            }
        }
    }
}