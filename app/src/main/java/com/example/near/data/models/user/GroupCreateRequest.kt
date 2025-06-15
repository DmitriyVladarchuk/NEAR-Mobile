package com.example.near.data.models.user

data class GroupCreateRequest(
    val groupName: String,
    val members: List<String>
)
