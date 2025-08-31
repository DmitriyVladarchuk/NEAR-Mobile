package com.example.near.feature.user.domain.models

import com.example.near.core.network.model.EmergencyType

data class UserTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
