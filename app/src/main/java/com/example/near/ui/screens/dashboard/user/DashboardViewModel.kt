package com.example.near.ui.screens.dashboard.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.UserTemplate
import com.example.near.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var notificationTemplates: List<UserTemplate> by mutableStateOf(listOf())
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                notificationTemplates = getUserUseCase()?.notificationTemplates ?: listOf()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }
}