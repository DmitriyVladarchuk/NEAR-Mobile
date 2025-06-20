package com.example.near.domain.shared.models

data class AuthCredentials(
    val accessToken: String,
    val refreshToken: String,
    val isCommunity: Boolean
)
