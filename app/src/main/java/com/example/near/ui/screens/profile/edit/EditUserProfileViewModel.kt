package com.example.near.ui.screens.profile.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.feature.user.domain.models.User
import com.example.near.domain.shared.models.UIState
import com.example.near.feature.user.domain.usecase.GetUserUseCase
import com.example.near.feature.user.domain.usecase.GetNotificationOptionsUseCase
import com.example.near.feature.user.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getNotificationOptionsUseCase: GetNotificationOptionsUseCase,
) : ViewModel() {

    private var user: User? = null

    private var _firstName by mutableStateOf("")
    var firstName
        get() = _firstName
        set(value) { _firstName = value }

    private var _lastName by mutableStateOf("")
    var lastName
        get() = _lastName
        set(value) { _lastName = value }

    private var _birthday by mutableStateOf("")
    var birthday
        get() = _birthday
        set(value) {
            _birthday = value
        }

    private var _country by mutableStateOf("")
    var country
        get() = _country
        set(value) { _country = value }

    private var _city by mutableStateOf("")
    var city
        get() = _city
        set(value) { _city = value }

    private var _district by mutableStateOf("")
    var district
        get() = _district
        set(value) { _district = value }

    private var _selectedOptions = mutableStateOf<List<Int>>(emptyList())
    val selectedOptions: State<List<Int>> = _selectedOptions

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                user = getUserUseCase()
                user?.let {
                    _firstName = it.firstName
                    _lastName = it.lastName
                    _birthday = it.birthday
                    _country = it.country
                    _city = it.city
                    _district = it.district
                }

                val optionsResult = getNotificationOptionsUseCase()
                if (optionsResult.isSuccess) {
                    _selectedOptions.value = optionsResult.getOrNull()?.map { it.id } ?: emptyList()
                } else {
                    UIState.Error("Failed to load user or notification options")
                }

                _uiState.value = UIState.Idle
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Failed to load user data")
            }
        }
    }

    fun toggleNotificationOption(optionId: Int) {
        _selectedOptions.value = if (_selectedOptions.value.contains(optionId)) {
            _selectedOptions.value.filterNot { it == optionId }
        } else {
            _selectedOptions.value + optionId
        }
    }

    fun submitChanges() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val result = updateUserUseCase(
                    firstName = firstName.takeIf { it.isNotBlank() },
                    lastName = lastName.takeIf { it.isNotBlank() },
                    birthday = birthday.takeIf { it.isNotBlank() },
                    country = country.takeIf { it.isNotBlank() },
                    city = city.takeIf { it.isNotBlank() },
                    district = district.takeIf { it.isNotBlank() },
                    selectedOptions = selectedOptions.value.takeIf { it.isNotEmpty() }
                )

                if (result.isSuccess) _uiState.value = UIState.Success
                else _uiState.value = UIState.Error("Update failed")

            } catch (e: Exception) {
                _uiState.value = UIState.Error("Update failed")
            }
        }
    }
}