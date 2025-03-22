package com.example.near.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.near.R
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder

@Composable
fun RegistrationAccount(modifier: Modifier = Modifier, viewModel: RegistrationAccountViewModel = viewModel()) {
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 40.dp)
    Column(modifier = defaultModifier.then(modifier)) {
        HeaderTextInfo()

        TextFieldAccount(viewModel)
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
private fun TextFieldAccount(viewModel: RegistrationAccountViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Name
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = CustomTheme.colors.content,
                unfocusedTextColor = CustomTheme.colors.content,
                focusedContainerColor = CustomTheme.colors.background,
                unfocusedLabelColor = CustomTheme.colors.background,
                cursorColor = CustomTheme.colors.currentContainer,
                focusedBorderColor = CustomTheme.colors.currentContainer,
                unfocusedBorderColor = CustomTheme.colors.container
            )
        )

        // Email
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            singleLine = true,
            label = {
                TextFieldLabel(stringResource(R.string.email))
            },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.email)) },
            textStyle = AppTypography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = CustomTheme.colors.content,
                unfocusedTextColor = CustomTheme.colors.content,
                focusedContainerColor = CustomTheme.colors.background,
                unfocusedLabelColor = CustomTheme.colors.background,
                cursorColor = CustomTheme.colors.currentContainer,
                focusedBorderColor = CustomTheme.colors.currentContainer,
                unfocusedBorderColor = CustomTheme.colors.container
            )
        )
    }
}