package com.example.near.ui.screens.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.common.storage.AuthDataStorage
import com.example.near.data.storage.SessionManager
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.shared.storage.SettingsDataStorage
import com.example.near.feature.auth.domain.model.AuthCheckResult
import com.example.near.feature.auth.domain.usecase.LoadUserUseCase
import com.example.near.feature.community.domain.repository.CommunityRepository
import com.example.near.feature.user.domain.repository.UserRepository
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
                when (val result = loadUserUseCase()) {
                    is AuthCheckResult.Authenticated -> {
                        refreshFCMToken()
                        handleSuccessfulAuth(result.isCommunity)
                        _uiState.value = UIState.Success
                    }
                    AuthCheckResult.EmailNotVerified -> {
                        _navigationRoute.value = Routes.EmailVerification.route
                        _uiState.value = UIState.Success
                    }
                    AuthCheckResult.NotAuthenticated -> {
                        //_navigationRoute.value = Routes.EmailVerification.route
                        _navigationRoute.value = Routes.Onboarding.route
                        _uiState.value = UIState.Success
                    }
                    is AuthCheckResult.Error -> {
                        _uiState.value = UIState.Error(result.exception ?: "Auth error")
                        _navigationRoute.value = Routes.Onboarding.route
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Unknown error")
                _navigationRoute.value = Routes.Onboarding.route
            }
        }
    }

    private fun handleSuccessfulAuth(isCommunity: Boolean) {
        _navigationRoute.value = if (isCommunity) {
            Routes.CommunityDashboard.route
        } else {
            Routes.Dashboards.route
        }
    }

    fun refreshFCMToken() = viewModelScope.launch {
        val token = fcmTokenManager.forceGenerateNewToken()
        token?.let {
            fcmTokenManager.handleNewToken(token)
        }
    }

}