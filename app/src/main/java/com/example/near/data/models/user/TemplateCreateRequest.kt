package com.example.near.data.models.user

import com.example.near.domain.models.user.EmergencyType

data class TemplateCreateRequest(
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)