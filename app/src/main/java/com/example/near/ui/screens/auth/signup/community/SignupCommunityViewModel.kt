package com.example.near.ui.screens.auth.signup.community

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.R
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.community.usecase.SignUpCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignupCommunityViewModel @Inject constructor(
    private val signUpCommunityUseCase: SignUpCommunityUseCase,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    var communityName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var monitoringRegion by mutableStateOf("")
    var selectedEmergencyTypes by mutableStateOf(emptyList<EmergencyType>())

    fun toggleEmergencyType(type: EmergencyType) {
        selectedEmergencyTypes = if (selectedEmergencyTypes.contains(type)) {
            selectedEmergencyTypes - type
        } else {
            selectedEmergencyTypes + type
        }
    }

    fun onSignUpClick(navigateToDashboards: () -> Unit) {
        if (!validateInput()) return

        _uiState.value = UIState.Loading
        viewModelScope.launch {
            signUpCommunityUseCase(
                communityName,
                email,
                password,
                monitoringRegion,
                selectedEmergencyTypes
            ).onSuccess {
                _uiState.value = UIState.Success
                navigateToDashboards()
            }.onFailure { handleError(it) }
        }
    }

    private fun validateInput(): Boolean {
        return when {
            communityName.isBlank() -> showError(R.string.error_name)
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                showError(R.string.error_invalid_email)
            password.isBlank() || password.length < 7 ->
                showError(R.string.error_password_weak)
            monitoringRegion.isBlank() -> showError(R.string.error_region)
            selectedEmergencyTypes.isEmpty() -> showError(R.string.error_emergency_type)
            else -> true
        }
    }

    private fun showError(errorRes: Int): Boolean {
        _uiState.value = UIState.Error(context.getString(errorRes))
        return false
    }

    private fun handleError(throwable: Throwable) {
        _uiState.value = UIState.Error(
            when (throwable) {
                is IOException -> context.getString(R.string.error_network)
                else -> context.getString(R.string.error_signup_failed)
            }
        )
    }
}