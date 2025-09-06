package com.example.near.ui.components.headers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

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

@Composable
fun SecondaryHeaderTextInfo(
    text: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "logo icon",
            modifier = Modifier.size(32.dp).clickable { onBack() },
            tint = CustomTheme.colors.content
        )
        Text(
            text = text,
            style = AppTypography.titleLarge,
            modifier = Modifier.padding(start = 8.dp),
            color = CustomTheme.colors.content,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "logo delete",
            modifier = Modifier.size(32.dp).clickable { onDelete() },
            tint = CustomTheme.colors.orange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TestMainHeaderTextInfo() {
    NEARTheme {
//        SecondaryHeaderTextInfo(text = "Test") {
//
//        }
        SecondaryHeaderTextInfo(text = "Test", onBack = {}, onDelete = {})
    }
}