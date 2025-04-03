package com.example.near.ui.views

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import com.example.near.ui.theme.CustomTheme

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CustomTheme.colors.content,
    unfocusedTextColor = CustomTheme.colors.content,
    focusedContainerColor = CustomTheme.colors.background,
    unfocusedContainerColor = CustomTheme.colors.background,
    unfocusedLabelColor = CustomTheme.colors.background,
    cursorColor = CustomTheme.colors.currentContainer,
    focusedBorderColor = CustomTheme.colors.currentContainer,
    unfocusedBorderColor = CustomTheme.colors.container
)