package com.example.near.data.models

import com.example.near.domain.models.NotificationOption

data class SignUpRequest(
    val userName: String,
    val email: String,
    val password: String,
    val location: String,
    val birthday: String,
    val selectedOptions: List<NotificationOption>
)
