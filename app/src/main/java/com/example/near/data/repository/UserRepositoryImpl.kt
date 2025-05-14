package com.example.near.data.repository

import android.util.Log
import com.example.near.data.API.UserService
import com.example.near.data.datastore.SessionManager
import com.example.near.data.models.FcmTokenRequest
import com.example.near.data.models.FriendRequest
import com.example.near.data.models.GroupActionRequest
import com.example.near.data.models.GroupCreateRequest
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.SignUpRequest
import com.example.near.data.models.TemplateCreateRequest
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.User
import com.example.near.domain.models.UserTemplate
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val sessionManager: SessionManager
) : UserRepository {

    override suspend fun signUp(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        phoneNumber: String,
        telegramShortName: String,
        selectedOptions: List<NotificationOption>
    ): Result<Unit> {
        return try {
            Log.d("Test", SignUpRequest(
                userName,
                email,
                password,
                location,
                birthday,
                phoneNumber,
                telegramShortName,
                selectedOptions
            ).toString())
            val response = userService.signUp(
                SignUpRequest(
                    userName,
                    email,
                    password,
                    location,
                    birthday,
                    phoneNumber,
                    telegramShortName,
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

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = userService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendFcmToken(token: String): Result<Unit> {
        return try {
            val response = userService.sendFcmToken("Bearer ${sessionManager.authToken!!.accessToken}", FcmTokenRequest(token))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(): Result<User> {
        return try {
            val response = userService.getUserInfo("Bearer ${sessionManager.authToken!!.accessToken}")
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

    override suspend fun getUserById(id: String): Result<User> {
        return try {
            val response = userService.getUserById("Bearer ${sessionManager.authToken!!.accessToken}", id)
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

    override suspend fun sendFriendRequest(friendId: String): Result<Unit> {
        return try {
            val response = userService.sendFriendRequest(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = FriendRequest(friendId = friendId)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFriendRequest(friendId: String): Result<Unit> {
        return try {
            val response = userService.addFriendRequest(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = FriendRequest(friendId = friendId)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectFriendRequest(friendId: String): Result<Unit> {
        return try {
            val response = userService.rejectFriendRequest(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = FriendRequest(friendId = friendId)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFriend(friendId: String): Result<Unit> {
        return try {
            val response = userService.removeFriend(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = FriendRequest(friendId = friendId)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createGroup(groupName: String, members: List<String>): Result<Unit> {
        return try {
            val response = userService.createGroup(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = GroupCreateRequest(groupName, members)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateGroup(id: String, groupName: String, members: List<String>): Result<Unit> {
        return try {
            val response = userService.updateGroup(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = GroupActionRequest(id, groupName, members)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGroup(id: String, groupName: String, members: List<String>): Result<Unit> {
        return try {
            val response = userService.deleteGroup(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = GroupActionRequest(id, groupName, members)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTemplate(
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return try {
            val response = userService.createTemplate(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = TemplateCreateRequest(templateName, message, emergencyType)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return try {
            val response = userService.updateTemplate(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = UserTemplate(id, templateName, message, emergencyType)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return try {
            val response = userService.deleteTemplate(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = UserTemplate(id, templateName, message, emergencyType)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}