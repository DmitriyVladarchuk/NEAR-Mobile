package com.example.near.ui.components.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun ForgotPassword(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Text(
        text = stringResource(R.string.forgot_password),
        style = AppTypography.labelSmall,
        color = CustomTheme.colors.content,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.clickable { onClick() },
        textAlign = TextAlign.End
    )
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordPreview() {
    NEARTheme {
        ForgotPassword(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {  }
    }
}