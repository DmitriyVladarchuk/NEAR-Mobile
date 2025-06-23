package com.example.near.ui.screens.auth.signup.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.shared.models.SignupNotificationOption
import com.example.near.domain.shared.models.UIState
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.components.common.AppTextField
import com.example.near.ui.components.auth.AuthScreenButtons
import com.example.near.ui.components.common.ErrorText
import com.example.near.ui.components.headers.HeaderTextInfo
import com.example.near.ui.components.auth.PasswordVisibilityToggle
import com.example.near.ui.components.transformations.DateTransformation


@Composable
fun SignupAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupAccountViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    navController: NavController
) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)
    val uiState by viewModel.uiState
    val scrollState = rememberScrollState()

    Box(
        modifier = defaultModifier.then(modifier).fillMaxSize()
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState).padding(bottom = 24.dp)) {
            HeaderTextInfo(
                firstText = stringResource(R.string.lets_get_you_started),
                secondText = stringResource(R.string.create_an_account),
                modifier = Modifier.padding(vertical = 40.dp)
            )
            AnimatedVisibility(
                uiState is UIState.Error
            ) {
                ErrorText(message = (uiState as UIState.Error).message)
            }
            TextFieldAccount(viewModel)
            NotificationOptions(viewModel)
        }

        AuthScreenButtons(
            enabled = uiState != UIState.Loading,
            modifier = Modifier.align(Alignment.BottomCenter),
            primaryButtonText = stringResource(R.string.get_started).uppercase(),
            secondaryText = stringResource(R.string.already_have_an_account),
            secondaryActionText = stringResource(R.string.login_here).uppercase(),
            onPrimaryButtonClick = {
                viewModel.onSignUpClick {
                    navController.navigate(Routes.Dashboards.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            onSecondaryActionClick = { onLoginClick() }
        )
    }
}

@Composable
private fun TextFieldAccount(viewModel: SignupAccountViewModel) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Name field
        AppTextField(
            value = viewModel.nameUser,
            onValueChange = { viewModel.nameUser = it },
            labelRes = R.string.your_name,
            placeholderRes = R.string.your_name
        )

        // Email field
        AppTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            labelRes = R.string.email,
            placeholderRes = R.string.email,
            keyboardType = KeyboardType.Email
        )

        // Password field
        AppTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            labelRes = R.string.password,
            placeholderRes = R.string.password,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (isPasswordVisible) None else PasswordVisualTransformation(),
            trailingIcon = {
                PasswordVisibilityToggle(
                    isVisible = isPasswordVisible,
                    onToggle = { isPasswordVisible = !isPasswordVisible }
                )
            }
        )

        // Country field
        AppTextField(
            value = viewModel.country,
            onValueChange = { viewModel.country = it },
            labelRes = R.string.country_city_district,
            placeholderRes = R.string.country_city_district
        )

        // Birthday field
        AppTextField(
            value = viewModel.birthday,
            onValueChange = { newText ->
                val filtered = newText.filter { it.isDigit() }.take(8)
                viewModel.birthday = filtered
            },
            labelRes = R.string.birthday,
            placeholderRes = R.string.birthday,
            keyboardType = KeyboardType.Number,
            visualTransformation = DateTransformation()
        )

        // Phone number
        AppTextField(
            value = viewModel.phone,
            onValueChange = { viewModel.phone = it },
            labelRes = R.string.phone_number,
            placeholderRes = R.string.phone_number,
            keyboardType = KeyboardType.Phone
        )

        // Telegram short name
        AppTextField(
            value = viewModel.telegramShortName,
            onValueChange = { viewModel.telegramShortName = it },
            labelRes = R.string.telegram_short_name,
            placeholderRes = R.string.telegram_short_name
        )
    }
}

@Composable
private fun NotificationOptions(viewModel: SignupAccountViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NotificationChip(
                label = stringResource(R.string.telegram),
                isSelected = viewModel.selectedNotifications.value.any { it.id == 2 },
                onToggle = { viewModel.toggleNotification(SignupNotificationOption(2, "Telegram")) }
            )

            NotificationChip(
                label = stringResource(R.string.email),
                isSelected = viewModel.selectedNotifications.value.any { it.id == 1 },
                onToggle = { viewModel.toggleNotification(SignupNotificationOption(1, "Email")) }
            )

            NotificationChip(
                label = stringResource(R.string.mobile_app),
                isSelected = viewModel.selectedNotifications.value.any { it.id == 3 },
                onToggle = { viewModel.toggleNotification(SignupNotificationOption(3, "Mobile Notification")) }
            )
        }
    }
}

@Composable
private fun NotificationChip(
    label: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val containerColor = if (isSelected) {
        CustomTheme.colors.currentContainer
    } else {
        CustomTheme.colors.background
    }

    val contentColor = if (isSelected) {
        CustomTheme.colors.currentContent
    } else {
        CustomTheme.colors.content
    }

    val borderColor = if (isSelected) {
        CustomTheme.colors.currentContent
    } else {
        CustomTheme.colors.container
    }

    OutlinedButton(
        onClick = { onToggle() },
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = label,
            style = AppTypography.bodySmall,
        )
    }
}
