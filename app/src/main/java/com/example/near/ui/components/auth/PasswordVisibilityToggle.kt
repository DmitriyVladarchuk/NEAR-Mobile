package com.example.near.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.ui.theme.NEARTheme

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

@Preview(showBackground = true)
@Composable
private fun PasswordVisibilityTogglePreview() {
    NEARTheme {
        Column {
            PasswordVisibilityToggle(isVisible = false) {  }
            Spacer(modifier = Modifier.height(20.dp))
            PasswordVisibilityToggle(isVisible = true) {  }
        }
    }
}