package com.example.near.feature.auth.data.mapper

import com.example.near.core.network.model.commmunity.SignUpCommunityRequest
import com.example.near.feature.auth.domain.model.CommunitySignup


fun SignUpCommunityRequest.toDomain(): CommunitySignup = CommunitySignup(
    communityName = communityName,
    email = email,
    password = password,
    location = location,
    monitoredEmergencyTypes = monitoredEmergencyTypes
)

fun CommunitySignup.toRequest(): SignUpCommunityRequest = SignUpCommunityRequest(
    communityName = communityName,
    email = email,
    password = password,
    location = location,
    monitoredEmergencyTypes = monitoredEmergencyTypes
)