package com.example.near.domain.repository

import com.example.near.data.models.LoginUserResponse
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.User

interface UserRepository {
    suspend fun signUp(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        selectedOptions: List<NotificationOption>
    ): Result<Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Result<LoginUserResponse>

    suspend fun getUserInfo(): Result<User>

    suspend fun getUserById(id: String): Result<User>

    suspend fun sendFriendRequest(friendId: String): Result<Unit>
}