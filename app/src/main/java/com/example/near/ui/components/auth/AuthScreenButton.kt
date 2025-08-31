package com.example.near.ui.components.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun AuthScreenButtons(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    primaryButtonText: String,
    secondaryText: String,
    secondaryActionText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryActionClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            enabled = enabled,
            onClick = onPrimaryButtonClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.orange,
                contentColor = Color.White
            )
        ) {
            Text(
                text = primaryButtonText.uppercase(),
                style = AppTypography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = secondaryText,
                style = AppTypography.labelSmall,
                color = CustomTheme.colors.content
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = secondaryActionText.uppercase(),
                style = AppTypography.bodySmall,
                color = CustomTheme.colors.currentContainer,
                modifier = Modifier.clickable(onClick = onSecondaryActionClick),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenButtonsPreview() {
    NEARTheme {
        AuthScreenButtons(
            primaryButtonText = "Click",
            secondaryText = "Other",
            secondaryActionText = "click",
            onPrimaryButtonClick = { },
            onSecondaryActionClick = {  }
        )
    }
}