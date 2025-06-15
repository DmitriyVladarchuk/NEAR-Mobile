package com.example.near.data.repository

import android.util.Log
import com.example.near.data.API.UserService
import com.example.near.data.datastore.SessionManager
import com.example.near.data.mapper.toDomain
import com.example.near.data.mapper.toRequest
import com.example.near.data.models.community.CommunityActionRequest
import com.example.near.data.models.user.FriendRequest
import com.example.near.data.models.user.GroupActionRequest
import com.example.near.data.models.user.GroupCreateRequest
import com.example.near.data.models.user.TemplateCreateRequest
import com.example.near.data.models.user.UserUpdateRequest
import com.example.near.data.shared.models.FcmTokenRequest
import com.example.near.data.shared.models.RefreshTokenRequest
import com.example.near.domain.models.common.AuthTokens
import com.example.near.domain.models.user.AllFriendsInfo
import com.example.near.domain.models.user.EmergencyType
import com.example.near.domain.models.user.LoginCredentials
import com.example.near.domain.models.user.NotificationOption
import com.example.near.domain.models.user.User
import com.example.near.domain.models.user.UserSignUp
import com.example.near.domain.models.user.UserTemplate
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val sessionManager: SessionManager
) : UserRepository {

    // Исправлено
    override suspend fun signUp(
        userSignUp: UserSignUp
    ): Result<Unit> {
        return try {
            val request = userSignUp.toRequest()
            val response = userService.signUp(request)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("SignUp error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Исправлено
    override suspend fun login(credentials: LoginCredentials): Result<AuthTokens> {
        return try {
            val response = userService.login(credentials.toRequest())
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Исправлено
    override suspend fun getNotificationOptions(): Result<List<NotificationOption>> {
        return try {
            val response = userService.getNotificationOptions(
                "Bearer ${sessionManager.authToken!!.accessToken}"
            )
            if (response.isSuccessful) {
                response.body()?.let { options ->
                    Result.success(options.map { it.toDomain() })
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Исправлено
    override suspend fun refreshToken(token: String): Result<AuthTokens> {
        return try {
            val response = userService.refreshToken(RefreshTokenRequest(token))

            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Refresh failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Исправлено
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
    // Исправлено
    override suspend fun getUserInfo(): Result<User> {
        return try {
            val response = userService.getUserInfo("Bearer ${sessionManager.authToken!!.accessToken}")
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
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
    // Исправлено
    override suspend fun getUserById(id: String): Result<User> {
        return try {
            val response = userService.getUserById("Bearer ${sessionManager.authToken!!.accessToken}", id)
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
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

    override suspend fun updateUser(
        firstName: String?,
        lastName: String?,
        birthday: String?,
        country: String?,
        city: String?,
        district: String?,
        selectedOptions: List<Int>?
    ): Result<Unit> {
        return try {
            val response = userService.updateUser(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = UserUpdateRequest(
                    firstName = firstName,
                    lastName = lastName,
                    birthday = birthday,
                    country = country,
                    city = city,
                    district = district,
                    selectedOptions = selectedOptions
                )
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Log.d("testEditRepo", response.code().toString())
                Result.failure(Exception("Error ${response.code()}: $errorBody"))

            }
        } catch (e: Exception) {
            Log.d("testEditRepo", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getAllFriendsInfo(): Result<AllFriendsInfo> {
        return try {
            val response = userService.getAllFriendsInfo(
                "Bearer ${sessionManager.authToken!!.accessToken}"
            )
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
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
                //friendId = friendId
            )
            Log.e("RemoveFriend ", response.message())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Log.e("RemoveFriend ", response.message())
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Log.e("RemoveFriend ", e.toString())
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

    override suspend fun userSubscribe(communityId: String): Result<Unit> {
        return try {
            val response = userService.userSubscribe(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = CommunityActionRequest(communityId)
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

    override suspend fun userCancelSubscribe(communityId: String): Result<Unit> {
        return try {
            val response = userService.userCancelSubscribe(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = CommunityActionRequest(communityId)
            )
            Log.e("cancelSubscribe ", response.message())
            if (response.isSuccessful) {
                Log.e("cancelSubscribe ", response.message())
                Result.success(Unit)
            } else {
                Log.e("cancelSubscribe ", response.code().toString())
                Result.failure(Exception("Failed to send friend request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}