package com.example.near.data.models

import com.example.near.domain.models.EmergencyType

data class TemplateCreateRequest(
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)