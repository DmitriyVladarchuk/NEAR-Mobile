package com.example.near.domain.models

data class UserGroup(
    val id: String,
    val groupName: String,
    val members: List<UserFriend>,
)