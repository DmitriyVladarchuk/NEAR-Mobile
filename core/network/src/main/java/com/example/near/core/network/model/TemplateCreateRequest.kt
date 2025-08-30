package com.example.near.core.network.model

import com.example.near.domain.shared.models.EmergencyType

data class TemplateCreateRequest(
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)