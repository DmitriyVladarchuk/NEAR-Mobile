package com.example.near.feature.auth.domain.model

data class AuthCredentials(
    val accessToken: String,
    val refreshToken: String,
    val isCommunity: Boolean
)