package com.example.near.data.mapper

import com.example.near.data.models.user.AllFriendsInfoResponse
import com.example.near.data.shared.models.EmergencyTypeResponse
import com.example.near.data.shared.models.LoginRequest
import com.example.near.data.shared.models.LoginResponse
import com.example.near.data.shared.models.NotificationOptionResponse
import com.example.near.data.shared.models.SignupNotificationOptionRequest
import com.example.near.data.models.user.UserFriendResponse
import com.example.near.data.models.user.UserGroupResponse
import com.example.near.data.models.user.UserResponse
import com.example.near.data.models.user.UserSignUpRequest
import com.example.near.data.models.user.UserSubscriptionResponse
import com.example.near.data.models.user.UserTemplateResponse
import com.example.near.domain.models.common.AuthTokens
import com.example.near.domain.models.user.AllFriendsInfo
import com.example.near.domain.models.user.EmergencyType
import com.example.near.domain.models.user.LoginCredentials
import com.example.near.domain.models.user.NotificationOption
import com.example.near.domain.models.user.SignupNotificationOption
import com.example.near.domain.models.user.User
import com.example.near.domain.models.user.UserFriend
import com.example.near.domain.models.user.UserGroup
import com.example.near.domain.models.user.UserSignUp
import com.example.near.domain.models.user.UserSubscription
import com.example.near.domain.models.user.UserTemplate

fun UserSignUp.toRequest(): UserSignUpRequest = UserSignUpRequest(
    userName = userName,
    email = email,
    password = password,
    phoneNumber = phoneNumber,
    telegramShortName = telegramShortName,
    location = location,
    birthday = birthday,
    selectedOptions = selectedOptions.map { it.toSignupRequest() }
)

fun SignupNotificationOption.toSignupRequest(): SignupNotificationOptionRequest =
    SignupNotificationOptionRequest(
        id = id,
        notificationOption = notificationOption
    )

fun LoginCredentials.toRequest(): LoginRequest = LoginRequest(
    email = email,
    password = password
)

fun LoginResponse.toDomain(): AuthTokens = AuthTokens(
    accessToken = accessToken,
    refreshToken = refreshToken,
    uuid = uuid
)

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