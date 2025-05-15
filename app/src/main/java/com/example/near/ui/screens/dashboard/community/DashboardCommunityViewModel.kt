package com.example.near.ui.screens.dashboard.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.UserTemplate
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.usecase.community.GetCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardCommunityViewModel @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase,
    private val communityRepository: CommunityRepository
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
                notificationTemplates = getCommunityUseCase()?.notificationTemplates ?: listOf()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load community data"
            } finally {
                isLoading = false
            }
        }
    }

    fun send(id: String) {
        viewModelScope.launch {
            val recipients = getCommunityUseCase().subscribers.map { it.id }
            communityRepository.sendTemplate(id, recipients)
        }
    }
}