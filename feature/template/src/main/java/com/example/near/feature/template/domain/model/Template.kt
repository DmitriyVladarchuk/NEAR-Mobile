package com.example.near.feature.template.domain.model

import com.example.near.core.network.model.EmergencyType

data class Template(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
)
