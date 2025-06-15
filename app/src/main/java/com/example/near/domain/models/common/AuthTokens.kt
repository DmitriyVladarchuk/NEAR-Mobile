package com.example.near.domain.models.common

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val uuid: String?
)
