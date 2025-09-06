package com.example.near.core.network

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String?,
    val uuid: String?
)