package com.example.near.domain.models.common

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    object Success : UIState()
    data class Error(val message: String) : UIState()
}