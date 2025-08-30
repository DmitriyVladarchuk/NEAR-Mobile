package com.example.near.domain.community.models

import com.example.near.domain.user.models.UserFriend
import com.example.near.user.models.UserTemplate

data class Community(
    val id: String,
    val communityName: String,
    val description: String?,
    val country: String,
    val city: String?,
    val district: String?,
    val registrationDate: String,
    val subscribers: List<UserFriend>,
    val notificationTemplates: List<UserTemplate>
)
