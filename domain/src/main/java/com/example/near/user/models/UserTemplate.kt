package com.example.near.user.models

import com.example.near.common.models.EmergencyType

data class UserTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
