package com.example.near.data.mapper

import com.example.near.data.models.user.NotificationOptionResponse
import com.example.near.domain.models.user.NotificationOption

fun NotificationOptionResponse.toDomain() = NotificationOption(
    id = id,
    title = title,
    color = color,
    bgColor = bgColor,
    colorDark = colorDark,
    bgColorDark = bgColorDark
)