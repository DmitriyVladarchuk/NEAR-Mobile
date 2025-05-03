package com.example.near.data.models

data class GroupCreateRequest(
    val groupName: String,
    val members: List<String>
)
