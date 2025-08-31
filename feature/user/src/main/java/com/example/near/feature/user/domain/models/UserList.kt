package com.example.near.feature.user.domain.models

data class UserList(
    val content: List<User>,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
