package com.example.near.common.models

sealed class AuthCheckResult {
    object NotAuthenticated : AuthCheckResult()
    object EmailNotVerified : AuthCheckResult()
    data class Authenticated(val isCommunity: Boolean) : AuthCheckResult()
    data class Error(val exception: String) : AuthCheckResult()
}