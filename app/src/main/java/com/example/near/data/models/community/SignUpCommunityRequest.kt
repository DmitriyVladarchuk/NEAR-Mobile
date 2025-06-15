package com.example.near.data.models.community

import com.example.near.domain.models.user.EmergencyType

data class SignUpCommunityRequest(
    val communityName: String,
    val email: String,
    val password: String,
    val location: String,
    val monitoredEmergencyTypes: List<EmergencyType>
)
