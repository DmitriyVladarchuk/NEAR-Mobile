package com.example.near.data.community.mappers

import com.example.near.community.models.CommunityUpdateParams
import com.example.near.core.network.model.commmunity.UpdateCommunityRequest
import com.example.near.core.network.model.commmunity.CommunityResponse
import com.example.near.core.network.model.commmunity.SignUpCommunityRequest
import com.example.near.domain.community.models.Community
import com.example.near.domain.community.models.CommunitySignup

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

fun CommunityUpdateParams.toRequest(): UpdateCommunityRequest {
    return UpdateCommunityRequest(
        communityName = communityName,
        description = description,
        country = country,
        city = city,
        district = district,
        emergencyTypeIds = emergencyTypeIds
    )
}