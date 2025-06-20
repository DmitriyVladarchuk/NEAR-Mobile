package com.example.near.domain.user.models

data class UserGroup(
    val id: String,
    val groupName: String,
    val members: List<UserFriend>,
)