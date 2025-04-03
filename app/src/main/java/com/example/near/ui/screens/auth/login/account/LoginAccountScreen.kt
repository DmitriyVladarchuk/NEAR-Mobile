package com.example.near.ui.screens.auth.login.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.near.R
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.AuthScreenButtons
import com.example.near.ui.views.ForgotPassword
import com.example.near.ui.views.HeaderTextInfo
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder
import com.example.near.ui.views.textFieldColors

@Composable
fun LoginAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginAccountViewModel = viewModel(),
    onSignUpClick: () -> Unit
) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 40.dp)

    Column(modifier = defaultModifier.then(modifier)) {
        HeaderTextInfo(
            stringResource(R.string.welcome_back).uppercase(),
            stringResource(R.string.login_to_your_account)
        )
        TextFieldAccount(viewModel)
        ForgotPassword {

        }
        AuthScreenButtons(
            primaryButtonText = stringResource(R.string.continue_text).uppercase(),
            secondaryText = stringResource(R.string.new_user),
            secondaryActionText = stringResource(R.string.sign_up_here).uppercase(),
            onPrimaryButtonClick = {
                //viewModel.onSignUpClick()
            },
            onSecondaryActionClick = { onSignUpClick() }
        )
    }
}

@Composable
private fun TextFieldAccount(viewModel: LoginAccountViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
    }
}