package com.example.near.ui.screens.templates.create

import com.example.near.core.network.model.EmergencyType

sealed interface TemplateEvent {
    data class LoadTemplate(val templateId: String?) : TemplateEvent
    data class UpdateTemplateName(val name: String) : TemplateEvent
    data class UpdateMessage(val message: String) : TemplateEvent
    data class SelectEmergencyType(val type: EmergencyType) : TemplateEvent
    data class SaveTemplate(val templateId: String?) : TemplateEvent
}