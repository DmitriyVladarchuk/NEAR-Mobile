package com.example.near.domain.usecase

import com.example.near.domain.models.NotificationOption
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        selectedOptions: List<NotificationOption>
    ): Result<Unit> {
        return userRepository.signUp(userName, email, password, location, birthday, selectedOptions)
    }
}