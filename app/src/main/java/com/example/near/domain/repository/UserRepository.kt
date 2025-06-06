package com.example.near.domain.repository

import com.example.near.data.models.LoginResponse
import com.example.near.domain.models.AllFriendsInfoResponse
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.User

interface UserRepository {
    suspend fun signUp(
        userName: String,
        email: String,
        password: String,
        location: String,
        birthday: String,
        phoneNumber: String,
        telegramShortName: String,
        selectedOptions: List<NotificationOption>
    ): Result<Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Result<LoginResponse>

    suspend fun refreshToken(token: String): Result<LoginResponse>

    suspend fun sendFcmToken(token: String): Result<Unit>

    suspend fun getUserInfo(): Result<User>

    suspend fun getUserById(id: String): Result<User>

    // --- Friends Actions ---

    suspend fun getAllFriendsInfo(): Result<AllFriendsInfoResponse>

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