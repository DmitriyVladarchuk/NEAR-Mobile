package com.example.near.data.models

import com.example.near.domain.models.NotificationOptionRequest

data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val age: Int,
    val country: String,
    val city: String,
    val district: String,
    val registrationDate: String,
    val friends: List<String>,
    val groups: List<String>,
    val subscriptions: List<String>,
    val notificationTemplates: List<NotificationOptionRequest>
)
