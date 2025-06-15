package com.example.near.data.models.community

data class SignUpRequest(
    val userName: String,
    val email: String,
    val password: String,
    val location: String,
    val birthday: String,
    val phoneNumber: String,
    val telegramShortName: String,
    val selectedOptions: List<NotificationOptionRequest>
)
