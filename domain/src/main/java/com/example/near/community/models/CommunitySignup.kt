package com.example.near.domain.community.models

import com.example.near.domain.shared.models.EmergencyType

data class CommunitySignup(
    val communityName: String,
    val email: String,
    val password: String,
    val location: String,
    val monitoredEmergencyTypes: List<EmergencyType>
)
