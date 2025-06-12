package com.example.near.ui.screens.profile.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.User
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
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

    // TODO добавить это поле
    private var _selectedOptions by mutableStateOf<List<Int>>(emptyList())
    var selectedOptions
        get() = _selectedOptions
        private set(value) { _selectedOptions = value }

    init {
        viewModelScope.launch {
            user = getUserUseCase()
            user.let {
                _firstName = user!!.firstName
                _lastName = user!!.lastName
                _birthday = user!!.birthday
                _country = user!!.country
                _city = user!!.city
                _district = user!!.district
            }
        }
    }

    fun submitChanges() {
        viewModelScope.launch {
            val result = updateUserUseCase(
                firstName = firstName.takeIf { it.isNotBlank() },
                lastName = lastName.takeIf { it.isNotBlank() },
                birthday = birthday.takeIf { it.isNotBlank() },
                country = country.takeIf { it.isNotBlank() },
                city = city.takeIf { it.isNotBlank() },
                district = district.takeIf { it.isNotBlank() },
                selectedOptions = selectedOptions.takeIf { it.isNotEmpty() }
            )

            Log.d("testEdit", result.isSuccess.toString())
        }
    }
}