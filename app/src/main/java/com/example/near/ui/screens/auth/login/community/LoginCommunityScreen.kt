package com.example.near.ui.screens.auth.login.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.example.near.domain.models.common.UIState
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.views.AppTextField
import com.example.near.ui.views.AuthScreenButtons
import com.example.near.ui.views.ErrorText
import com.example.near.ui.views.ForgotPassword
import com.example.near.ui.views.HeaderTextInfo
import com.example.near.ui.views.PasswordVisibilityToggle

@Composable
fun LoginCommunityScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginCommunityViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    navController: NavController
) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 40.dp)
    val uiState by viewModel.uiState

    Column(modifier = defaultModifier.then(modifier)) {
        HeaderTextInfo(
            stringResource(R.string.welcome_back).uppercase(),
            stringResource(R.string.login_in_as_a_community)
        )
        TextFieldCommunity(viewModel, (uiState as? UIState.Error)?.message)

        if (uiState is UIState.Error) ErrorText((uiState as UIState.Error).message)

        ForgotPassword { }
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
private fun TextFieldCommunity(viewModel: LoginCommunityViewModel, errorMessage: String?) {
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