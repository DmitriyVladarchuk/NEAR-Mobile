package com.example.near.ui.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.near.R

@Composable
fun PasswordVisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = stringResource(
                if (isVisible) R.string.hide_password else R.string.show_password
            )
        )
    }
}