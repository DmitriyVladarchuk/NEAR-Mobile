package com.example.near.user.models

import com.example.near.domain.user.models.User

data class UserList(
    val content: List<User>,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
