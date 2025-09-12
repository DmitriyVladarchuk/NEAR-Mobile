package com.example.near.ui.screens.templates.info

sealed interface InfoTemplateEffect {
    data object NavigateBack : InfoTemplateEffect
    data class ShowError(val error: TemplateError) : InfoTemplateEffect
}