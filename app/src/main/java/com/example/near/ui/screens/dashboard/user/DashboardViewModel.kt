package com.example.near.ui.screens.dashboard.user

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.shared.models.UIState
import com.example.near.user.models.UserTemplate
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.feature.template.domain.usecase.DeleteTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val deleteTemplateUseCase: DeleteTemplateUseCase
): ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    var notificationTemplates: List<UserTemplate> by mutableStateOf(listOf())
        private set


    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                notificationTemplates = getUserUseCase()?.notificationTemplates ?: listOf()
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Failed to load user data: ${e.message}")
            } finally {
                _uiState.value = UIState.Idle
            }
        }
    }

    fun deleteTemplate(template: UserTemplate) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                deleteTemplateUseCase(
                    template.id,
                    template.templateName,
                    template.message,
                    template.emergencyType
                ).onSuccess {
                    _uiState.value = UIState.Success
                    loadData()
                }.onFailure {
                    _uiState.value = UIState.Error("Failed to delete template: ${it.message}")
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Error: ${e.localizedMessage}")
            } finally {
                _uiState.value = UIState.Idle
            }
        }
    }
}