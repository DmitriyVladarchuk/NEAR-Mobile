package com.example.near.common.models

data class LoginCredentials(
    val email: String,
    val password: String,
    val isCommunity: Boolean,
)