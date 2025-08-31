package com.example.near.feature.user.domain.models

data class UserSubscription(
    val id: String,
    val communityName: String,
    val description: String? = null,
    val country: String,
    val city: String? = null,
    val district: String? = null,
    val registrationDate: String,
    val subscribersCount: Int,
)
