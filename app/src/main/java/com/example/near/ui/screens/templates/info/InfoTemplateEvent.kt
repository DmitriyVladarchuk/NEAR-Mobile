package com.example.near.ui.screens.templates.info

sealed interface InfoTemplateEvent {
    data class LoadData(val templateId: String) : InfoTemplateEvent
    data class ToggleRecipient(val friendId: String) : InfoTemplateEvent
    data class ToggleGroupRecipients(val members: List<String>) : InfoTemplateEvent
    data object SendTemplate : InfoTemplateEvent
}