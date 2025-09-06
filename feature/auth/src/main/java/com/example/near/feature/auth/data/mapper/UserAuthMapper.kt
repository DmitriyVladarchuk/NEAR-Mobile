package com.example.near.feature.auth.data.mapper

import com.example.near.core.network.AuthTokens
import com.example.near.feature.auth.domain.model.LoginCredentials
import com.example.near.core.network.model.LoginRequest
import com.example.near.core.network.model.LoginResponse
import com.example.near.core.network.model.SignupNotificationOptionRequest
import com.example.near.core.network.model.user.UserSignUpRequest
import com.example.near.feature.auth.domain.model.SignupNotificationOption
import com.example.near.feature.auth.domain.model.UserSignUp

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