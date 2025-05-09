package com.example.near.data.models.community

import com.example.near.domain.models.UserFriend
import com.example.near.domain.models.UserTemplate

data class CommunityResponse(
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
