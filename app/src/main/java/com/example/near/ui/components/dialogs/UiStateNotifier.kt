package com.example.near.ui.components.dialogs

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.near.domain.shared.models.UIState

@Composable
fun UiStateNotifier(
    state: UIState,
    context: Context,
    successMessage: String,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    LaunchedEffect(state) {
        when (state) {
            is UIState.Success -> {
                Toast.makeText(
                    context,
                    successMessage,
                    Toast.LENGTH_SHORT
                ).show()

                onSuccess()
            }
            is UIState.Error -> {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()

                onError()
            }
            else -> {}
        }
    }
}