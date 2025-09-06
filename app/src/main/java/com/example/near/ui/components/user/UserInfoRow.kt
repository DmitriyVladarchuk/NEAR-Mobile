package com.example.near.ui.components.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun UserInfoRow(
    icon: ImageVector,
    label: String,
    value: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp).padding(end = 8.dp),
            tint = CustomTheme.colors.content
        )

        if (value != null) {
            Text(
                text = "$label:",
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = value,
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = label,
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoRowThemedPreview() {
    NEARTheme {
        Column {
            UserInfoRow(
                icon = Icons.Default.Settings,
                label = "Настройки",
                modifier = Modifier.padding(16.dp)
            )

            UserInfoRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = "user@example.com",
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}