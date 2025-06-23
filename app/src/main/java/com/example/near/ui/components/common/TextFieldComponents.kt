package com.example.near.ui.components.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme

@Composable
fun TextFieldPlaceholder(
    text: String,
    style: TextStyle = AppTypography.bodyMedium
) {
    Text(
        text = text,
        style = style,
        color = CustomTheme.colors.container
    )
}

@Composable
fun TextFieldLabel(
    text: String,
    style: TextStyle = AppTypography.bodySmall
) {
    Text(
        text = text,
        style = style,
        color = CustomTheme.colors.container
    )
}