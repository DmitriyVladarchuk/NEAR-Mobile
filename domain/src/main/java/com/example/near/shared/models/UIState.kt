package com.example.near.domain.shared.models

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    object Success : UIState()
    data class Error(val message: String) : UIState()
}