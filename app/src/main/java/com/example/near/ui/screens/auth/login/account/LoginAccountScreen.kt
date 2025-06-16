package com.example.near.ui.screens.auth.login.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.common.UIState
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.views.AppTextField
import com.example.near.ui.views.AuthScreenButtons
import com.example.near.ui.views.ForgotPassword
import com.example.near.ui.views.HeaderTextInfo

@Composable
fun LoginAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginAccountViewModel = hiltViewModel(),
    navController: NavController,
    onSignUpClick: () -> Unit
) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 40.dp)
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) { viewModel.resetState() }

    Column(modifier = defaultModifier.then(modifier)) {
        HeaderTextInfo(
            stringResource(R.string.welcome_back).uppercase(),
            stringResource(R.string.login_to_your_account)
        )
        TextFieldAccount(viewModel, (uiState as? UIState.Error)?.message)

        if (uiState is UIState.Error) ErrorText((uiState as UIState.Error).message)

        ForgotPassword {

        }
        AuthScreenButtons(
            enabled = uiState != UIState.Loading,
            primaryButtonText = stringResource(R.string.continue_text).uppercase(),
            secondaryText = stringResource(R.string.new_user),
            secondaryActionText = stringResource(R.string.sign_up_here).uppercase(),
            onPrimaryButtonClick = {
                viewModel.login {
                    navController.navigate(Routes.Dashboards.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            onSecondaryActionClick = { onSignUpClick() }
        )
    }
}

@Composable
private fun TextFieldAccount(viewModel: LoginAccountViewModel, errorMessage: String?) {
    var isPasswordVisibility by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppTextField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            labelRes = R.string.email,
            placeholderRes = R.string.email,
            keyboardType = KeyboardType.Email,
            isError = errorMessage != null && viewModel.email.isBlank()
        )

        AppTextField(
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            labelRes = R.string.password,
            placeholderRes = R.string.password,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (isPasswordVisibility) None else PasswordVisualTransformation(),
            trailingIcon = {
                PasswordVisibilityToggle(
                    isVisible = isPasswordVisibility,
                    onToggle = { isPasswordVisibility = !isPasswordVisibility }
                )
            },
            isError = errorMessage != null && viewModel.password.isBlank()
        )
    }
}

@Composable
private fun PasswordVisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = stringResource(
                if (isVisible) R.string.hide_password else R.string.show_password
            )
        )
    }
}

@Composable
private fun ErrorText(message: String) {
    Text(
        text = message,
        color = Color.Red,
        style = AppTypography.bodySmall,
        modifier = Modifier.padding(top = 4.dp)
    )
}
