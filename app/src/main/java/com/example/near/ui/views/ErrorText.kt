package com.example.near.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.near.ui.theme.AppTypography

@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        color = Color.Red,
        style = AppTypography.bodySmall,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}