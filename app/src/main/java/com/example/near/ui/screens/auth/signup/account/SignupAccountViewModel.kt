package com.example.near.ui.screens.auth.signup.account

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.R
import com.example.near.domain.shared.models.SignupNotificationOption
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.user.usecase.auth.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignupAccountViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    var nameUser by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var country by mutableStateOf("")
    var birthday by mutableStateOf("")
    var phone by mutableStateOf("")
    var telegramShortName by mutableStateOf("")

    val selectedNotifications = mutableStateOf(setOf<SignupNotificationOption>())

    fun toggleNotification(option: SignupNotificationOption) {
        selectedNotifications.value = if (selectedNotifications.value.contains(option)) {
            selectedNotifications.value - option
        } else {
            selectedNotifications.value + option
        }
    }

    fun onSignUpClick(navigateToDashboards: () -> Unit) {
        if (!validateInput()) return

        _uiState.value = UIState.Loading

        viewModelScope.launch {
            signUpUserUseCase(
                nameUser,
                email,
                password,
                country,
                birthday,
                phone,
                telegramShortName,
                selectedNotifications.value.toList()
            ).onSuccess {
                _uiState.value = UIState.Success
                navigateToDashboards()
            }.onFailure { handleError(it) }
        }
    }

    private fun validateInput(): Boolean {
        return when {
            nameUser.isBlank() -> showError(R.string.error_name)
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                showError(R.string.error_invalid_email)
            password.isBlank() || password.length < 7 ->
                showError(R.string.error_password_weak)
            country.isBlank() -> showError(R.string.error_country)
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