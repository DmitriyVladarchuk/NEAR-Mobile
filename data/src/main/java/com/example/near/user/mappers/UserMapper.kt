package com.example.near.data.user.mappers

import com.example.near.core.network.model.EmergencyType
import com.example.near.core.network.model.EmergencyTypeResponse
import com.example.near.core.network.model.NotificationOptionResponse
import com.example.near.core.network.model.user.AllFriendsInfoResponse
import com.example.near.core.network.model.user.CommunitiesListResponse
import com.example.near.core.network.model.user.UserFriendResponse
import com.example.near.core.network.model.user.UserGroupResponse
import com.example.near.core.network.model.user.UserListResponse
import com.example.near.core.network.model.user.UserResponse
import com.example.near.core.network.model.user.UserSubscriptionResponse
import com.example.near.core.network.model.user.UserTemplateResponse
import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.user.models.User
import com.example.near.domain.user.models.UserFriend
import com.example.near.domain.user.models.UserGroup
import com.example.near.domain.user.models.UserSubscription
import com.example.near.user.models.UserTemplate
import com.example.near.user.models.CommunitiesList
import com.example.near.user.models.UserList

fun AllFriendsInfoResponse.toDomain(): AllFriendsInfo = AllFriendsInfo(
    friends = friends,
    sentRequests = sentRequests,
    receivedRequests = receivedRequests
)

fun UserResponse.toDomain(): User = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    birthday = birthday,
    age = age,
    country = country,
    city = city,
    district = district,
    registrationDate = registrationDate,
    deviceToken = deviceToken,
    friends = friends.map { it.toDomain() },
    groups = groups.map { it.toDomain() },
    subscriptions = subscriptions.map { it.toDomain() },
    notificationTemplates = notificationTemplates.map { it.toDomain() }
)

fun UserFriendResponse.toDomain(): UserFriend = UserFriend(
    id = id,
    firstName = firstName,
    lastName = lastName,
    birthday = birthday,
    age = age,
    country = country,
    city = city,
    district = district,
    registrationDate = registrationDate,
    friendsCount = friendsCount,
    subscriptionCount = subscriptionCount
)

fun UserGroupResponse.toDomain(): UserGroup = UserGroup(
    id = id,
    groupName = groupName,
    members = members.map { it.toDomain() }
)

fun UserSubscriptionResponse.toDomain(): UserSubscription = UserSubscription(
    id = id,
    communityName = communityName,
    description = description,
    country = country,
    city = city,
    district = district,
    registrationDate = registrationDate,
    subscribersCount = subscribersCount
)

fun EmergencyTypeResponse.toDomain(): EmergencyType = EmergencyType(
    id = id,
    title = title,
    color = color,
    bgColor = bgColor
)

fun UserTemplateResponse.toDomain(): UserTemplate = UserTemplate(
    id = id,
    templateName = templateName,
    message = message,
    emergencyType = emergencyType.toDomain()
)

fun NotificationOptionResponse.toDomain() = NotificationOption(
    id = id,
    title = title,
    color = color,
    bgColor = bgColor,
    colorDark = colorDark,
    bgColorDark = bgColorDark
)

fun CommunitiesListResponse.toDomain(): CommunitiesList = CommunitiesList(
    content = content.map { it.toDomain() },
    size = size,
    totalPages = totalPages,
    totalElements = totalElements
)

fun UserListResponse.toDomain(): UserList = UserList(
    content = content.map { it.toDomain() },
    size = size,
    totalPages = totalPages,
    totalElements = totalElements
)
