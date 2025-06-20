package com.example.near.domain.user.models

import com.example.near.domain.shared.models.EmergencyType

data class UserTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
