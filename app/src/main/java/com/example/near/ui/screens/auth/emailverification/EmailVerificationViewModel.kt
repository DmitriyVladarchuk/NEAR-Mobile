package com.example.near.ui.screens.auth.emailverification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.feature.auth.domain.model.AuthCheckResult
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.domain.shared.models.UIState
import com.example.near.feature.auth.domain.usecase.LoadUserUseCase
import com.example.near.service.FcmTokenManager
import com.example.near.ui.screens.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val loadUserUseCase: LoadUserUseCase,
    private val emailVerificationStorage: EmailVerificationStorage,
    private val fcmTokenManager: FcmTokenManager,
) : ViewModel() {

    private val _navigationRoute = MutableStateFlow<String?>(null)
    val navigationRoute: StateFlow<String?> = _navigationRoute.asStateFlow()

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    fun checkVerification() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                when (val result = loadUserUseCase()) {
                    is AuthCheckResult.Authenticated -> {
                        _uiState.value = UIState.Success
                        handleSuccessfulAuth(result.isCommunity)
                        refreshFCMToken()
                    }
                    is AuthCheckResult.EmailNotVerified -> {
                        _uiState.value = UIState.Idle
                    }
                    is AuthCheckResult.NotAuthenticated -> {
                        emailVerificationStorage.clearPendingEmail()
                        _uiState.value = UIState.Error("Session expired")
                    }
                    is AuthCheckResult.Error -> {
                        _uiState.value = UIState.Error(result.exception)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.localizedMessage ?: "Unknown error")
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
        fcmTokenManager.forceGenerateNewToken()
    }
}