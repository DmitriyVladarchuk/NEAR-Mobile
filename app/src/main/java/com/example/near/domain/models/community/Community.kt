package com.example.near.domain.models.community

import com.example.near.domain.models.user.UserFriend
import com.example.near.domain.models.user.UserTemplate

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
