package com.example.near.ui.screens.auth.login.community

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.R
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.community.usecase.LoginCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginCommunityViewModel @Inject constructor(
    private val loginCommunityUseCase: LoginCommunityUseCase,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun resetState() {
        _uiState.value = UIState.Idle
    }

    fun login(navigateToDashboards: () -> Unit) {
        if (!validateData()) return

        _uiState.value = UIState.Loading
        viewModelScope.launch {
            loginCommunityUseCase(email, password)
                .onSuccess {
                    _uiState.value = UIState.Success
                    navigateToDashboards()
                }
                .onFailure { handleError(it) }
        }
    }

    private fun validateData(): Boolean {
        return when {
            email.isBlank() -> {
                _uiState.value = UIState.Error(context.getString(R.string.error_email))
                false
            }
            password.isBlank() -> {
                _uiState.value = UIState.Error(context.getString(R.string.error_password))
                false
            }
            else -> true
        }
    }

    private fun handleError(throwable: Throwable) {
        _uiState.value = UIState.Error(
            when (throwable) {
                is IOException -> context.getString(R.string.error_network)
                else -> context.getString(R.string.error_auth)
            }
        )
    }
}