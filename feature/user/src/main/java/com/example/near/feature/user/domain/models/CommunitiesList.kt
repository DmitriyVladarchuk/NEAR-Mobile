package com.example.near.feature.user.domain.models

data class CommunitiesList(
    val content: List<UserSubscription>,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
