package com.example.near.common.models

data class AuthCredentials(
    val accessToken: String,
    val refreshToken: String,
    val isCommunity: Boolean
)