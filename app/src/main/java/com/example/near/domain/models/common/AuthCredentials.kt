package com.example.near.domain.models.common

data class AuthCredentials(
    val accessToken: String,
    val refreshToken: String,
    val isCommunity: Boolean
)
