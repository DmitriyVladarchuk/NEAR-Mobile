package com.example.near.data.user.repositories

import android.util.Log
import com.example.near.core.network.model.FcmTokenRequest
import com.example.near.core.network.model.commmunity.CommunityActionRequest
import com.example.near.core.network.model.user.FriendRequest
import com.example.near.core.network.model.user.GroupActionRequest
import com.example.near.core.network.model.user.GroupCreateRequest
import com.example.near.core.network.model.user.UserUpdateRequest
import com.example.near.core.network.service.UserService
import com.example.near.data.community.mappers.toDomain
import com.example.near.data.storage.SessionManager
import com.example.near.data.user.mappers.toDomain
import com.example.near.domain.community.models.Community
import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.user.models.User
import com.example.near.domain.user.repository.UserRepository
import com.example.near.user.models.CommunitiesList
import com.example.near.user.models.UserList

class UserRepositoryImpl(
    private val userService: UserService,
    private val sessionManager: SessionManager,
) : UserRepository {

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

    override suspend fun getCommunityById(id: String): Result<Community> {
        return try {
            Log.d("UserRep", id)
            val response = userService.getCommunityById("Bearer ${sessionManager.authToken!!.accessToken}", id)
            Log.d("UserRep", response.code().toString())
            Log.d("UserRep", response.body().toString())
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

    override suspend fun searchUsersByValue(value: String): Result<UserList> {
        return try {
            val response = userService.searchUsers(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                value
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

    override suspend fun getAllCommunities(): Result<CommunitiesList> {
        return try {
            val response = userService.getAllCommunities("Bearer ${sessionManager.authToken!!.accessToken}")
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

    override suspend fun searchCommunityByValue(value: String): Result<CommunitiesList> {
        return try {
            val response = userService.searchCommunities(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                value
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