package com.example.near.core.network.model


data class TemplateCreateRequest(
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)