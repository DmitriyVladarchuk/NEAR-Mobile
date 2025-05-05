package com.example.near.domain.models

data class UserTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)
