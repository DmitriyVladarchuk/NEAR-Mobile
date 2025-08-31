package com.example.near.feature.auth.domain.model

import com.example.near.core.network.model.EmergencyType

data class CommunitySignup(
    val communityName: String,
    val email: String,
    val password: String,
    val location: String,
    val monitoredEmergencyTypes: List<EmergencyType>
)