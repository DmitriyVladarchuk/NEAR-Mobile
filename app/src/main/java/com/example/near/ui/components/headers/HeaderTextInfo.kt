package com.example.near.ui.components.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun HeaderTextInfo(firstText: String, secondText: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
private fun HeaderTextInfoPreview() {
    NEARTheme {
        HeaderTextInfo(
            firstText = "First",
            secondText = "Second",
            modifier = Modifier.padding(vertical = 40.dp)
        )
    }
}