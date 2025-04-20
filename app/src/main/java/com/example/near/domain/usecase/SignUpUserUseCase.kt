package com.example.near.domain.usecase

import com.example.near.domain.models.NotificationOption
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
        selectedOptions: List<NotificationOption>
    ): Boolean {
        if (userRepository.signUp(userName, email, password, location, birthday, phoneNumber, telegramShortName, selectedOptions).isSuccess) {
            loginUserUseCase(email, password)
            return true
        } else {
            return false
        }
    }
}