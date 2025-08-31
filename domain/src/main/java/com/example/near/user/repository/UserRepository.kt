package com.example.near.domain.user.repository

import com.example.near.domain.community.models.Community
import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.user.models.User
import com.example.near.user.models.CommunitiesList
import com.example.near.user.models.UserList

interface UserRepository {

    suspend fun getNotificationOptions(): Result<List<NotificationOption>>

    suspend fun sendFcmToken(token: String): Result<Unit>

    suspend fun getUserInfo(): Result<User>

    suspend fun getUserById(id: String): Result<User>

    suspend fun getCommunityById(id: String): Result<Community>

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

    suspend fun searchUsersByValue(value: String): Result<UserList>

    suspend fun sendFriendRequest(friendId: String): Result<Unit>

    suspend fun addFriendRequest(friendId: String): Result<Unit>

    suspend fun rejectFriendRequest(friendId: String): Result<Unit>

    suspend fun removeFriend(friendId: String): Result<Unit>

    // --- Groups Actions ---

    suspend fun createGroup(groupName: String, members: List<String>): Result<Unit>

    suspend fun updateGroup(id: String, groupName: String, members: List<String>): Result<Unit>

    suspend fun deleteGroup(id: String, groupName: String, members: List<String>): Result<Unit>

    suspend fun getAllCommunities(): Result<CommunitiesList>

    suspend fun searchCommunityByValue(value: String): Result<CommunitiesList>

    suspend fun userSubscribe(
        communityId: String
    ): Result<Unit>

    suspend fun userCancelSubscribe(
        communityId: String
    ): Result<Unit>

}