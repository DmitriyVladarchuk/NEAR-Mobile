package com.example.near.data.repository

import com.example.near.data.API.UserService
import com.example.near.data.models.SignUpRequest
import com.example.near.data.models.UserResponse
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun signUp(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        selectedOptions: List<NotificationOption>
    ): Result<Unit> {
        return try {
            val response = userService.signUp(
                SignUpRequest(
                    userName,
                    email,
                    password,
                    location,
                    birthday,
                    selectedOptions
                )
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(token: String): Result<UserResponse> {
        return try {
            val response = userService.getUserInfo("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}