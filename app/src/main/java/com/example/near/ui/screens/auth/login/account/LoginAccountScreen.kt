package com.example.near.ui.screens.auth.login.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.shared.models.UIState
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.components.common.AppTextField
import com.example.near.ui.components.auth.AuthScreenButtons
import com.example.near.ui.components.common.ErrorText
import com.example.near.ui.components.auth.ForgotPassword
import com.example.near.ui.components.headers.HeaderTextInfo
import com.example.near.ui.components.auth.PasswordVisibilityToggle

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
            firstText = stringResource(R.string.welcome_back).uppercase(),
            secondText = stringResource(R.string.login_to_your_account),
            modifier = Modifier.padding(vertical = 40.dp)
        )
        TextFieldAccount(viewModel, (uiState as? UIState.Error)?.message)

        if (uiState is UIState.Error) ErrorText((uiState as UIState.Error).message)

        ForgotPassword(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) { }
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
