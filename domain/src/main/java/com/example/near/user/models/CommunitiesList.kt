package com.example.near.user.models

import com.example.near.domain.user.models.UserSubscription

data class CommunitiesList(
    val content: List<UserSubscription>,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
