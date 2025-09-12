package com.example.near.ui.screens.templates.create

import com.example.near.core.network.model.EmergencyType

data class TemplateState(
    val templateName: String = "",
    val message: String = "",
    val selectedEmergencyType: EmergencyType? = null,
    val isLoading: Boolean = false,
    val validationErrors: Set<Field> = emptySet(),
    val isSuccess: Boolean = false
)

enum class Field { TEMPLATE_NAME, MESSAGE, EMERGENCY_TYPE }