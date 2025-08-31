package com.example.near.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun SubmittedTemplateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.submitted_templates),
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.list),
                contentDescription = null,
                modifier = Modifier.weight(1f),
                tint = CustomTheme.colors.content
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmittedTemplateButtonPreview() {
    NEARTheme {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
        ) {
            SubmittedTemplateButton(
                onClick = { /* обработка клика */ },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}