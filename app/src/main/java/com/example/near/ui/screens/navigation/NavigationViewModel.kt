package com.example.near.ui.screens.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.data.storage.SessionManager
import com.example.near.data.storage.SettingsDataStorage
import com.example.near.domain.models.common.UIState
import com.example.near.domain.repository.AuthDataStorage
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.user.auth.LoadUserUseCase
import com.example.near.service.FcmTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    val sessionManager: SessionManager,
    val authDataStorage: AuthDataStorage,
    val settingsDataStorage: SettingsDataStorage,
    val userRepository: UserRepository,
    val communityRepository: CommunityRepository,
    val loadUserUseCase: LoadUserUseCase,
    val fcmTokenManager: FcmTokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _navigationRoute = MutableStateFlow<String?>(null)
    val navigationRoute: StateFlow<String?> = _navigationRoute.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                loadUserUseCase().fold(
                    onSuccess = {
                        handleSuccessfulAuth()
                        _uiState.value = UIState.Success
                    },
                    onFailure = { e ->
                        _uiState.value = UIState.Error(e.message ?: "Auth error")
                        _navigationRoute.value = Routes.Onboarding.route
                    }
                )
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Unknown error")
                _navigationRoute.value = Routes.Onboarding.route
            }
        }
    }

    private fun handleSuccessfulAuth() {
        val credentials = authDataStorage.getCredentials() ?: run {
            _navigationRoute.value = Routes.Onboarding.route
            return
        }

        _navigationRoute.value = if (credentials.isCommunity) {
            Routes.CommunityDashboard.route
        } else {
            Routes.Dashboards.route
        }
    }

    fun refreshFCMToken() = viewModelScope.launch {
        fcmTokenManager.forceGenerateNewToken()
    }

}