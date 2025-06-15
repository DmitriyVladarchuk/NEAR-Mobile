package com.example.near.data.user.models

data class GroupActionRequest(
    val id: String,
    val groupName: String,
    val members: List<String>
)
