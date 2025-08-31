package com.example.near.feature.user.domain.models

data class UserGroup(
    val id: String,
    val groupName: String,
    val members: List<UserFriend>,
)