package com.example.near.domain.models.user

data class UserGroup(
    val id: String,
    val groupName: String,
    val members: List<UserFriend>,
)