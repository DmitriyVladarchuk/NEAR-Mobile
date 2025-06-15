package com.example.near.domain.models.user

import com.example.near.domain.models.common.EmergencyType

data class UserTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
