package com.example.near.ui.screens.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.core.network.model.EmergencyType
import com.example.near.user.models.UserTemplate
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.community.usecase.GetCommunityUseCase
import com.example.near.feature.template.domain.usecase.CreateTemplateUseCase
import com.example.near.feature.template.domain.usecase.UpdateTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTemplateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getCommunityUseCase: GetCommunityUseCase,
    private val createTemplateUseCase: CreateTemplateUseCase,
    private val updateTemplateUseCase: UpdateTemplateUseCase,
    private val communityRepository: CommunityRepository
) : ViewModel() {

    var template by mutableStateOf<UserTemplate?>(null)

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

    fun loadTemplate(templateId: String?, isCommunity: Boolean) {
        viewModelScope.launch {
            isLoading = true
            try {
                val templates = if (isCommunity) {
                    getCommunityUseCase()?.notificationTemplates
                } else {
                    getUserUseCase()?.notificationTemplates
                }

                template = templates?.find { it.id == templateId }
                template?.let {
                    templateName = it.templateName
                    message = it.message
                    selectedEmergencyType = it.emergencyType
                }
            } catch (e: Exception) {
                error = e.message ?: "Failed to load template"
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

    fun saveTemplate(templateId: String?, isCommunity: Boolean, onSuccess: () -> Unit) {
        if (templateName.isBlank() || message.isBlank() || selectedEmergencyType == null) {
            error = "Please fill all fields"
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                if (templateId != null) {
                    if (isCommunity) {
                        //updateCommunityTemplateUseCase(templateId, templateName, message, selectedEmergencyType!!)
                        communityRepository.updateTemplate(templateId, templateName, message, selectedEmergencyType!!)
                    } else {
                        updateTemplateUseCase(templateId, templateName, message, selectedEmergencyType!!)
                    }
                } else {
                    if (isCommunity) {
                        //createCommunityTemplateUseCase(templateName, message, selectedEmergencyType!!)
                        communityRepository.createTemplate(templateName, message, selectedEmergencyType!!)
                    } else {
                        createTemplateUseCase(templateName, message, selectedEmergencyType!!)
                    }
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