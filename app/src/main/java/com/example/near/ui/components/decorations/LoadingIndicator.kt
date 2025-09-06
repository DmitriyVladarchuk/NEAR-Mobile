package com.example.near.ui.components.decorations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.domain.shared.models.UIState
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun LoadingIndicator(state: UIState, modifier: Modifier = Modifier) {
    if (state is UIState.Loading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(CustomTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = CustomTheme.colors.orange)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    NEARTheme {
        LoadingIndicator(
            state = UIState.Loading,
            modifier = Modifier
        )
    }
}