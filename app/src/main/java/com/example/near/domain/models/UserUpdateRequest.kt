package com.example.near.domain.models

data class UserUpdateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val birthday: String? = null,
    val country: String? = null,
    val city: String? = null,
    val district: String? = null,
    val selectedOptions: List<Int>? = null
)
