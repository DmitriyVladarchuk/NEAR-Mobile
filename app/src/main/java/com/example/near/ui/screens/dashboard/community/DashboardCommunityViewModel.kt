package com.example.near.ui.screens.dashboard.community

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.user.models.UserTemplate
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.community.usecase.GetCommunityUseCase
import com.example.near.domain.shared.models.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardCommunityViewModel @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase,
    private val communityRepository: CommunityRepository
): ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    var notificationTemplates: List<UserTemplate> by mutableStateOf(listOf())
        private set


    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                notificationTemplates = getCommunityUseCase().notificationTemplates
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Failed to load user data: ${e.message}")
            }
            finally {
                _uiState.value = UIState.Idle
            }
        }
    }

    fun send(id: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val recipients = getCommunityUseCase().subscribers.map { it.id }
                communityRepository.sendTemplate(id, recipients)
                    .onSuccess { _uiState.value = UIState.Success }
                    .onFailure { _uiState.value = UIState.Error("Failed to send notification") }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}