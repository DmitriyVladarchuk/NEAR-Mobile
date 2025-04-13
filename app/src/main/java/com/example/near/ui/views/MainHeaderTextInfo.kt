package com.example.near.ui.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme

@Composable
fun MainHeaderTextInfo(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.logo_svg),
            contentDescription = "logo icon",
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = stringResource(R.string.app_name),
            style = AppTypography.titleLarge,
            modifier = Modifier.padding(start = 8.dp),
            color = CustomTheme.colors.content,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = text,
            style = AppTypography.titleLarge,
            color = CustomTheme.colors.content,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TestMainHeaderTextInfo() {
    NEARTheme {
        MainHeaderTextInfo(text = "Test")
    }
}