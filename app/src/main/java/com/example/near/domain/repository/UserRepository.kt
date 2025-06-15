package com.example.near.domain.repository

import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.models.user.User
import com.example.near.domain.models.common.AuthTokens
import com.example.near.domain.models.user.AllFriendsInfo
import com.example.near.domain.models.common.LoginCredentials
import com.example.near.domain.models.common.NotificationOption
import com.example.near.domain.models.user.UserSignUp

interface UserRepository {
    // Исправил
    suspend fun signUp(userSignUp: UserSignUp): Result<Unit>
    // Исправил
    suspend fun login(credentials: LoginCredentials): Result<AuthTokens>
    // Исправил
    suspend fun getNotificationOptions(): Result<List<NotificationOption>>
    // Исправил
    suspend fun refreshToken(token: String): Result<AuthTokens>
    // Исправил
    suspend fun sendFcmToken(token: String): Result<Unit>
    // Исправил
    suspend fun getUserInfo(): Result<User>
    // Исправил
    suspend fun getUserById(id: String): Result<User>

    suspend fun updateUser(
        firstName: String? = null,
        lastName: String? = null,
        birthday: String? = null,
        country: String? = null,
        city: String? = null,
        district: String? = null,
        selectedOptions: List<Int>? = null
    ): Result<Unit>

    // --- Friends Actions ---

    suspend fun getAllFriendsInfo(): Result<AllFriendsInfo>

    suspend fun sendFriendRequest(friendId: String): Result<Unit>

    suspend fun addFriendRequest(friendId: String): Result<Unit>

    suspend fun rejectFriendRequest(friendId: String): Result<Unit>

    suspend fun removeFriend(friendId: String): Result<Unit>

    // --- Groups Actions ---

    suspend fun createGroup(groupName: String, members: List<String>): Result<Unit>

    suspend fun updateGroup(id: String, groupName: String, members: List<String>): Result<Unit>

    suspend fun deleteGroup(id: String, groupName: String, members: List<String>): Result<Unit>

    // --- Template ---

    suspend fun createTemplate(
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit>

    suspend fun updateTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit>

    suspend fun deleteTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit>

    suspend fun userSubscribe(
        communityId: String
    ): Result<Unit>

    suspend fun userCancelSubscribe(
        communityId: String
    ): Result<Unit>

}