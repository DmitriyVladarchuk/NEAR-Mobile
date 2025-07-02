package com.example.near.ui.screens.auth.emailverification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.shared.models.UIState
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme

@Composable
fun EmailVerificationScreen(
    navController: NavController,
    viewModel: EmailVerificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.checkVerification()
    }

    LaunchedEffect(viewModel.navigationRoute) {
        if (uiState is UIState.Success) {
            navController.navigate(viewModel.navigationRoute) {
                popUpTo(Routes.Splash.route) { inclusive = true }
            }
        }
    }

    NEARTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.background)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MarkEmailRead,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = CustomTheme.colors.orange
                )

                Text(
                    text = stringResource(R.string.email_verification_title),
                    style = AppTypography.titleLarge,
                    color = CustomTheme.colors.content
                )

                Text(
                    text = stringResource(R.string.email_verification_message),
                    style = AppTypography.bodyMedium,
                    color = CustomTheme.colors.content,
                    textAlign = TextAlign.Center
                )

                when (uiState) {
                    UIState.Loading -> {
                        CircularProgressIndicator(color = CustomTheme.colors.orange)
                    }
                    UIState.Idle -> {
                        Button(
                            onClick = { viewModel.checkVerification() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CustomTheme.colors.orange,
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.i_confirmed_email))
                        }
                    }
                    is UIState.Error -> {
                        Text(
                            text = (uiState as UIState.Error).message,
                            color = Color.Red,
                            style = AppTypography.bodySmall
                        )
                        Button(
                            onClick = { viewModel.checkVerification() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CustomTheme.colors.orange,
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}