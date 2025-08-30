package com.example.near.common.models

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String?,
    val uuid: String?
)