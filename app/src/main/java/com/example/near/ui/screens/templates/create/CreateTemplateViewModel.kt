package com.example.near.ui.screens.templates.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.core.network.model.EmergencyType
import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.usecase.CreateTemplateUseCase
import com.example.near.feature.template.domain.usecase.GetTemplatesUseCase
import com.example.near.feature.template.domain.usecase.UpdateTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTemplateViewModel @Inject constructor(
    private val getTemplatesUseCase: GetTemplatesUseCase,
    private val createTemplateUseCase: CreateTemplateUseCase,
    private val updateTemplateUseCase: UpdateTemplateUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TemplateState())
    val state: StateFlow<TemplateState> = _state.asStateFlow()

    fun processEvent(event: TemplateEvent) {
        when (event) {
            is TemplateEvent.LoadTemplate -> loadTemplate(event.templateId)
            is TemplateEvent.UpdateTemplateName -> {
                updateTemplateName(event.name)
                clearFieldError(Field.TEMPLATE_NAME)
            }
            is TemplateEvent.UpdateMessage -> {
                updateMessage(event.message)
                clearFieldError(Field.MESSAGE)
            }
            is TemplateEvent.SelectEmergencyType -> {
                selectEmergencyType(event.type)
                clearFieldError(Field.EMERGENCY_TYPE)
            }
            is TemplateEvent.SaveTemplate -> saveTemplate(event.templateId)
        }
    }

    private fun loadTemplate(templateId: String?) {
        viewModelScope.launch {
            setLoading(true)

            try {
                val templatesResult = getTemplatesUseCase()

                if (templatesResult.isSuccess) {
                    val templates = templatesResult.getOrNull() ?: emptyList()
                    val template = templateId?.let { id -> templates.find { it.id == id } }
                    template?.let { updateStateWithTemplate(it) }
                }
            } finally {
                setLoading(false)
            }
        }
    }

    private fun saveTemplate(templateId: String?) {
        val validationErrors = validateFields()
        if (validationErrors.isNotEmpty()) {
            setValidationError(validationErrors)
            return
        }

        viewModelScope.launch {
            setLoading(true)

            try {
                val currentState = _state.value

                if (templateId != null) {
                    updateTemplate(templateId, currentState)
                } else {
                    createTemplate(currentState)
                }

                setSuccess()
            } finally {
                setLoading(false)
            }
        }
    }

    private suspend fun updateTemplate(templateId: String, state: TemplateState) {
        val template = Template(
            id = templateId,
            templateName = state.templateName,
            message = state.message,
            emergencyType = state.selectedEmergencyType!!
        )
        updateTemplateUseCase(template)
    }

    private suspend fun createTemplate(state: TemplateState) {
        val template = CreateTemplate(
            templateName = state.templateName,
            message = state.message,
            emergencyType = state.selectedEmergencyType!!
        )
        createTemplateUseCase(template)
    }

    private fun updateStateWithTemplate(template: Template) {
        _state.update {
            it.copy(
                templateName = template.templateName,
                message = template.message,
                selectedEmergencyType = template.emergencyType,
                isLoading = false,
                validationErrors = emptySet()
            )
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading, validationErrors = emptySet()) }
    }

    private fun setValidationError(fields: Set<Field>) {
        _state.update { it.copy(isLoading = false, validationErrors = fields) }
    }

    private fun setSuccess() {
        _state.update {
            it.copy(isLoading = false, isSuccess = true, validationErrors = emptySet())
        }
    }

    private fun validateFields(): Set<Field> {
        val currentState = _state.value
        val errors = mutableSetOf<Field>()

        if (currentState.templateName.isBlank()) {
            errors.add(Field.TEMPLATE_NAME)
        }
        if (currentState.message.isBlank()) {
            errors.add(Field.MESSAGE)
        }
        if (currentState.selectedEmergencyType == null) {
            errors.add(Field.EMERGENCY_TYPE)
        }

        return errors
    }

    private fun updateTemplateName(name: String) {
        _state.update { it.copy(templateName = name) }
    }

    private fun updateMessage(message: String) {
        _state.update { it.copy(message = message) }
    }

    private fun selectEmergencyType(type: EmergencyType) {
        _state.update { it.copy(selectedEmergencyType = type) }
    }

    private fun clearFieldError(field: Field) {
        _state.update { it.copy(validationErrors = it.validationErrors - field) }
    }
}
