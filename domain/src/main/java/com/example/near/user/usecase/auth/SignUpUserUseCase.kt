package com.example.near.domain.user.usecase.auth

import com.example.near.domain.shared.models.SignupNotificationOption
import com.example.near.domain.user.models.UserSignUp
import com.example.near.domain.user.repository.UserRepository

class SignUpUserUseCase(
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
        selectedOptions: List<SignupNotificationOption>
    ): Result<Unit> {
        val result = userRepository.signUp(
            UserSignUp(
                userName = userName,
                email = email,
                password = password,
                phoneNumber = phoneNumber,
                telegramShortName = telegramShortName,
                location = location,
                birthday = birthday,
                selectedOptions = selectedOptions
            )
        )
        if (result.isSuccess)
            loginUserUseCase(email, password)
        return result
    }
}