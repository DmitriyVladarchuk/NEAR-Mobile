package com.example.near.ui.screens.auth.signup.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.NotificationOption
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.AuthScreenButtons
import com.example.near.ui.views.HeaderTextInfo
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder
import com.example.near.ui.views.textFieldColors
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun SignupAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupAccountViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    navController: NavController
) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)

    val scrollState = rememberScrollState()

    Column(modifier = defaultModifier.then(modifier).verticalScroll(scrollState)) {
        HeaderTextInfo(
            stringResource(R.string.lets_get_you_started),
            stringResource(R.string.create_an_account)
        )
        TextFieldAccount(viewModel)
        NotificationOptions(viewModel)

        AuthScreenButtons(
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
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Name field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.nameUser,
            onValueChange = { viewModel.nameUser = it },
            singleLine = true,
            label = {
                TextFieldLabel(stringResource(R.string.your_name))
            },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.your_name)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors()
        )

        // Email field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = {
                TextFieldLabel(stringResource(R.string.email))
            },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.email)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors()
        )

        // Password field
        var passwordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { TextFieldLabel(stringResource(R.string.password)) },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.password)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password),
                        tint = CustomTheme.colors.content
                    )
                }
            }
        )

        // Country, city, district field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.country,
            onValueChange = { viewModel.country = it },
            singleLine = true,
            label = {
                TextFieldLabel(stringResource(R.string.country_city_district))
            },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.country_city_district)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors()
        )

        // Birthday field
        val dateFormatter = remember {
            SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.birthday,
            onValueChange = { newValue ->
                viewModel.birthday = newValue
            },
            singleLine = true,
            label = { TextFieldLabel(stringResource(R.string.birthday)) },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.birthday)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        // Phone number
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.phone,
            onValueChange = { newValue ->
                viewModel.phone = newValue
            },
            singleLine = true,
            label = { TextFieldLabel(stringResource(R.string.phone_number)) },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.phone_number)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        // Telegram short name
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.telegramShortName,
            onValueChange = { viewModel.telegramShortName = it },
            singleLine = true,
            label = {
                TextFieldLabel(stringResource(R.string.telegram_short_name))
            },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.telegram_short_name)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors()
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
                onToggle = { viewModel.toggleNotification(NotificationOption(2, "Telegram")) }
            )

            NotificationChip(
                label = stringResource(R.string.email),
                isSelected = viewModel.selectedNotifications.value.any { it.id == 1 },
                onToggle = { viewModel.toggleNotification(NotificationOption(1, "Email")) }
            )

            NotificationChip(
                label = stringResource(R.string.mobile_app),
                isSelected = viewModel.selectedNotifications.value.any { it.id == 3 },
                onToggle = { viewModel.toggleNotification(NotificationOption(3, "Mobile Notification")) }
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
