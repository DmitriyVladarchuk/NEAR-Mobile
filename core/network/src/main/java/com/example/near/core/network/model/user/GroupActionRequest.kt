package com.example.near.core.network.model.user

data class GroupActionRequest(
    val id: String,
    val groupName: String,
    val members: List<String>
)
