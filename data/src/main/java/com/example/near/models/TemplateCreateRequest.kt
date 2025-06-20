package com.example.near.data.shared.models

import com.example.near.domain.shared.models.EmergencyType

data class TemplateCreateRequest(
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)