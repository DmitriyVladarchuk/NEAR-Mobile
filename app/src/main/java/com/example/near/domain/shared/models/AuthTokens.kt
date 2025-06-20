package com.example.near.domain.shared.models

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String?,
    val uuid: String?
)
