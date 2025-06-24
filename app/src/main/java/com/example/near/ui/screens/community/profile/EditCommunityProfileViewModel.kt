package com.example.near.ui.screens.community.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.community.models.Community
import com.example.near.domain.community.usecase.GetCommunityUseCase
import com.example.near.domain.shared.models.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCommunityProfileViewModel  @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase,
) : ViewModel() {

    private var community: Community? = null

    private var _communityName by mutableStateOf("")
    var communityName
        get() = _communityName
        set(value) { _communityName = value }

    private var _description by mutableStateOf("")
    var description
        get() = _description
        set(value) { _description = value }

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

    private var _emergencyTypeIds = mutableStateOf<List<Int>>(emptyList())
    val emergencyTypeIds: State<List<Int>> = _emergencyTypeIds

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState


    init {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                community = getCommunityUseCase()
                community?.let {
                    _communityName = it.communityName
                    _description = it.description ?: ""
                    _country = it.country
                    _city = it.city ?: ""
                    _district = it.district ?: ""
                }

//                val optionsResult = getNotificationOptionsUseCase()
//                if (optionsResult.isSuccess) {
//                    _selectedOptions.value = optionsResult.getOrNull()?.map { it.id } ?: emptyList()
//                } else {
//                    UIState.Error("Failed to load community or notification options")
//                }

                _uiState.value = UIState.Idle
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Failed to load community data")
            }
        }
    }
}