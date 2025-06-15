package com.example.near.domain.usecase.user.auth

import com.example.near.domain.models.user.SignupNotificationOption
import com.example.near.domain.models.user.UserSignUp
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