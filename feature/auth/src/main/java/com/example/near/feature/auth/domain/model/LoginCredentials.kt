package com.example.near.feature.auth.domain.model

data class LoginCredentials(
    val email: String,
    val password: String,
    val isCommunity: Boolean,
)