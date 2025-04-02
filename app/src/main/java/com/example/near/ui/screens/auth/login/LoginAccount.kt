package com.example.near.ui.screens.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.near.R
import com.example.near.domain.models.NotificationType
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun RegistrationAccount(
    modifier: Modifier = Modifier,
    viewModel: LoginAccountViewModel = viewModel(),
    onSignInClick: () -> Unit
) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 40.dp)

    Column(modifier = defaultModifier.then(modifier)) {
        HeaderTextInfo()
        TextFieldAccount(viewModel)
        NotificationOptions(viewModel)
        ActionButtons(
            onSignUpClick = { viewModel.onSignUpClick() },
            onSignInClick = onSignInClick
        )
    }
}

@Composable
private fun HeaderTextInfo() {
    Column(
        modifier = Modifier.padding(vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.lets_get_you_started),
            style = AppTypography.bodySmall,
            color = CustomTheme.colors.content
        )

        Text(
            text = stringResource(R.string.create_an_account),
            style = AppTypography.titleLarge,
            color = CustomTheme.colors.content
        )
    }
}

@Composable
private fun TextFieldAccount(viewModel: LoginAccountViewModel) {
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
    }
}

@Composable
private fun NotificationOptions(viewModel: LoginAccountViewModel) {
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
                isSelected = viewModel.selectedNotifications.value.contains(NotificationType.TELEGRAM),
                onToggle = { viewModel.toggleNotification(NotificationType.TELEGRAM) }
            )

            NotificationChip(
                label = stringResource(R.string.email),
                isSelected = viewModel.selectedNotifications.value.contains(NotificationType.EMAIL),
                onToggle = { viewModel.toggleNotification(NotificationType.EMAIL) }
            )

            NotificationChip(
                label = stringResource(R.string.mobile_app),
                isSelected = viewModel.selectedNotifications.value.contains(NotificationType.MOBILE_APP),
                onToggle = { viewModel.toggleNotification(NotificationType.MOBILE_APP) }
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

@Composable
private fun ActionButtons(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onSignUpClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.orange,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.get_started).uppercase(),
                style = AppTypography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                style = AppTypography.labelSmall,
                color = CustomTheme.colors.content
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.login_here).uppercase(),
                style = AppTypography.bodySmall,
                color = CustomTheme.colors.currentContainer,
                modifier = Modifier.clickable(onClick = onSignInClick),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CustomTheme.colors.content,
    unfocusedTextColor = CustomTheme.colors.content,
    focusedContainerColor = CustomTheme.colors.background,
    unfocusedContainerColor = CustomTheme.colors.background,
    unfocusedLabelColor = CustomTheme.colors.background,
    cursorColor = CustomTheme.colors.currentContainer,
    focusedBorderColor = CustomTheme.colors.currentContainer,
    unfocusedBorderColor = CustomTheme.colors.container
)
