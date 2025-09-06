package com.example.near.core.network.model

data class TemplateResponse(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
