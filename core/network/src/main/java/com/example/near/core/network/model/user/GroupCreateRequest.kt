package com.example.near.core.network.model.user

data class GroupCreateRequest(
    val groupName: String,
    val members: List<String>
)
