package com.example.near.domain.models.user

data class UserTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
