package com.example.near.data.community.mappers

import com.example.near.data.community.models.CommunityResponse
import com.example.near.data.community.models.SignUpCommunityRequest
import com.example.near.domain.models.community.Community
import com.example.near.domain.models.community.CommunitySignup

fun CommunityResponse.toDomain(): Community = Community(
    id = id,
    communityName = communityName,
    description = description,
    country = country,
    city = city,
    district = district,
    registrationDate = registrationDate,
    subscribers = subscribers,
    notificationTemplates = notificationTemplates,
)

fun SignUpCommunityRequest.toDomain(): CommunitySignup = CommunitySignup(
    communityName = communityName,
    email = email,
    password = password,
    location = location,
    monitoredEmergencyTypes = monitoredEmergencyTypes
)