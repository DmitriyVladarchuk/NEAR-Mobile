package com.example.near.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme

@Composable
fun SecondaryHeaderTextInfo(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "logo icon",
            modifier = Modifier.size(32.dp).clickable { onClick() },
            tint = CustomTheme.colors.content
        )
        Text(
            text = text,
            style = AppTypography.titleLarge,
            modifier = Modifier.padding(start = 8.dp),
            color = CustomTheme.colors.content,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TestMainHeaderTextInfo() {
    NEARTheme {
        SecondaryHeaderTextInfo(text = "Test") {

        }
    }
}