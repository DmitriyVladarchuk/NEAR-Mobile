package com.example.near.domain.repository

import com.example.near.data.models.UserResponse
import com.example.near.domain.models.NotificationOption

interface UserRepository {
    suspend fun signUp(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        selectedOptions: List<NotificationOption>
    ): Result<Unit>

    suspend fun getUserInfo(token: String): Result<UserResponse>
}