package com.example.near.ui.screens.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.user.template.CreateTemplateUseCase
import com.example.near.domain.usecase.user.template.UpdateTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTemplateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val createTemplateUseCase: CreateTemplateUseCase,
    private val updateTemplateUseCase: UpdateTemplateUseCase
) : ViewModel() {

    var templateName by mutableStateOf("")
        private set

    var message by mutableStateOf("")
        private set

    var selectedEmergencyType by mutableStateOf<EmergencyType?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadTemplate(templateId: String?) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val template = getUserUseCase()?.notificationTemplates?.find { it.id == templateId }
                templateName = template?.templateName ?: ""
                message = template?.message ?: ""
                selectedEmergencyType = template?.emergencyType
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateTemplateName(name: String) {
        templateName = name
    }

    fun updateMessage(msg: String) {
        message = msg
    }

    fun selectEmergencyType(type: EmergencyType) {
        selectedEmergencyType = type
    }

    fun saveTemplate(templateId: String?, onSuccess: () -> Unit) {
        if (templateName.isBlank() || message.isBlank() || selectedEmergencyType == null) {
            error = "Please fill all fields"
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                if (templateId != null) {
                    updateTemplateUseCase(templateId, templateName, message, selectedEmergencyType!!)
                } else {
                    createTemplateUseCase(templateName, message, selectedEmergencyType!!)
                }

                onSuccess()
            } catch (e: Exception) {
                error = "Failed to save template: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun dismissError() {
        error = null
    }

}