package com.example.near.feature.auth.domain.model

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