package com.example.near.domain.usecase.user.auth

import com.example.near.domain.models.NotificationOptionRequest
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val loginUserUseCase: LoginUserUseCase
) {
    suspend operator fun invoke(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        phoneNumber: String,
        telegramShortName: String,
        selectedOptions: List<NotificationOptionRequest>
    ): Result<Unit> {
        val result = userRepository.signUp(userName, email, password, location, birthday, phoneNumber, telegramShortName, selectedOptions)
        if (result.isSuccess)
            loginUserUseCase(email, password)
        return result
    }
}