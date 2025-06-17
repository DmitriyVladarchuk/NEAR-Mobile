package com.example.near.ui.screens.auth.signup.community

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.models.common.UIState
import com.example.near.domain.models.common.emergencyTypes
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.dark_content
import com.example.near.ui.views.AppTextField
import com.example.near.ui.views.AuthScreenButtons
import com.example.near.ui.views.ErrorText
import com.example.near.ui.views.HeaderTextInfo
import com.example.near.ui.views.PasswordVisibilityToggle

@Composable
fun SignupCommunityScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupCommunityViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    navController: NavController
) {
    val uiState by viewModel.uiState
    val scrollState = rememberScrollState()
    val defaultModifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)

    Box(
        modifier = defaultModifier.then(modifier).fillMaxSize()
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState).padding(bottom = 24.dp)) {
            HeaderTextInfo(
                stringResource(R.string.lets_get_you_started),
                stringResource(R.string.create_a_community)
            )
            AnimatedVisibility(
                uiState is UIState.Error
            ) {
                ErrorText(message = (uiState as UIState.Error).message)
            }
            TextFieldCommunity(viewModel)
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
private fun TextFieldCommunity(viewModel: SignupCommunityViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Community Name
        AppTextField(
            value = viewModel.communityName,
            onValueChange = { viewModel.communityName = it },
            labelRes = R.string.community_name,
            placeholderRes = R.string.community_name
        )

        // Email
        AppTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            labelRes = R.string.email,
            placeholderRes = R.string.email,
            keyboardType = KeyboardType.Email
        )

        // Password
        var isPasswordVisible by remember { mutableStateOf(false) }
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

        // Monitoring Region
        AppTextField(
            value = viewModel.monitoringRegion,
            onValueChange = { viewModel.monitoringRegion = it },
            labelRes = R.string.monitoring_region,
            placeholderRes = R.string.monitoring_region
        )

        // Emergency Types
        Text(
            text = stringResource(R.string.select_emergency_types),
            style = AppTypography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        EmergencyTypeGrid(
            selectedTypes = viewModel.selectedEmergencyTypes,
            onTypeSelected = viewModel::toggleEmergencyType
        )
    }
}

@Composable
private fun EmergencyTypeGrid(
    selectedTypes: List<EmergencyType>,
    onTypeSelected: (EmergencyType) -> Unit
) {
    val columns = 3
    val chunkedList = emergencyTypes.chunked(columns)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        chunkedList.forEach { rowTypes ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowTypes.forEach { type ->
                    EmergencyTypeChip(
                        type = type,
                        isSelected = selectedTypes.contains(type),
                        onSelected = { onTypeSelected(type) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Добавляем пустые элементы для выравнивания
                if (rowTypes.size < columns) {
                    repeat(columns - rowTypes.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun EmergencyTypeChip(
    type: EmergencyType,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = Color(type.color.toColorInt())

    FilterChip(
        selected = isSelected,
        onClick = onSelected,
        label = {
            Text(
                text = type.title,
                style = AppTypography.bodySmall,
                color = if (isSelected) dark_content else textColor
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = textColor,
            containerColor = CustomTheme.colors.background,
            selectedLabelColor = CustomTheme.colors.background,
            labelColor = textColor
        ),
        modifier = modifier
    )
}
