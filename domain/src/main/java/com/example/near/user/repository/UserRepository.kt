package com.example.near.domain.user.repository

import com.example.near.common.models.EmailVerificationStatus
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.domain.user.models.User
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.shared.models.LoginCredentials
import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.user.models.UserSignUp
import com.example.near.user.models.CommunitiesList

interface UserRepository {

    suspend fun signUp(userSignUp: UserSignUp): Result<EmailVerificationStatus>

    suspend fun login(credentials: LoginCredentials): Result<EmailVerificationStatus>

    suspend fun getNotificationOptions(): Result<List<NotificationOption>>

    suspend fun refreshToken(): Result<Unit>

    suspend fun sendFcmToken(token: String): Result<Unit>

    suspend fun getUserInfo(): Result<User>

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

    suspend fun getAllCommunities(): Result<CommunitiesList>

    suspend fun searchCommunityByValue(value: String): Result<CommunitiesList>

    suspend fun userSubscribe(
        communityId: String
    ): Result<Unit>

    suspend fun userCancelSubscribe(
        communityId: String
    ): Result<Unit>

}