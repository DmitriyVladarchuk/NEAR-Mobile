package com.example.near.domain.user.models

import com.example.near.domain.shared.models.SignupNotificationOption

data class UserSignUp(
    val userName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val telegramShortName: String,
    val location: String,
    val birthday: String,
    val selectedOptions: List<SignupNotificationOption>
)
