package com.example.near.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme

@Composable
fun HeaderTextInfo(firstText: String, secondText: String) {
    Column(
        modifier = Modifier.padding(vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = firstText,
            style = AppTypography.bodySmall,
            color = CustomTheme.colors.content
        )

        Text(
            text = secondText,
            style = AppTypography.titleLarge,
            color = CustomTheme.colors.content
        )
    }
}