package com.example.near.data.user.models

data class GroupCreateRequest(
    val groupName: String,
    val members: List<String>
)
