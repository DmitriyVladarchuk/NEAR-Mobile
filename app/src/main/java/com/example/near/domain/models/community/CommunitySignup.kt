package com.example.near.domain.models.community

import com.example.near.domain.models.common.EmergencyType

data class CommunitySignup(
    val communityName: String,
    val email: String,
    val password: String,
    val location: String,
    val monitoredEmergencyTypes: List<EmergencyType>
)
