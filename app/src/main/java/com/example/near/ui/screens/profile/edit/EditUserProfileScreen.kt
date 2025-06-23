package com.example.near.ui.screens.profile.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.shared.models.UIState
import com.example.near.ui.components.common.AppTextField
import com.example.near.ui.components.dialogs.UiStateNotifier
import com.example.near.ui.components.headers.SecondaryHeaderTextInfo
import com.example.near.ui.components.transformations.DateTransformation
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme

@Composable
fun EditUserProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EditUserProfileViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState
    val context = LocalContext.current

    UiStateNotifier(
        state = uiState,
        context = context,
        successMessage = "Profile updated successfully",
        onSuccess = { navController.popBackStack() },
    )

    Box(modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LoadingIndicator(uiState)
        ScrollableForms(viewModel, navController)
        SaveButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClick = viewModel::submitChanges
        )
    }
}

@Composable
private fun ScrollableForms(
    viewModel: EditUserProfileViewModel,
    navController: NavController
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(R.string.edit_profile),
            modifier = Modifier.padding(vertical = 16.dp)
        ) { navController.popBackStack() }

        ProfileForm(viewModel)
        NotificationOptions(viewModel)
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun ProfileForm(viewModel: EditUserProfileViewModel) {
    Column(Modifier) {
        PersonalInfoSection(viewModel)
        LocationSection(viewModel)
    }
}

@Composable
private fun PersonalInfoSection(viewModel: EditUserProfileViewModel) {
    AppTextField(
        value = viewModel.firstName,
        onValueChange = { viewModel.firstName = it },
        labelRes = R.string.first_name,
        placeholderRes = R.string.first_name
    )

    AppTextField(
        value = viewModel.lastName,
        onValueChange = { viewModel.lastName = it },
        labelRes = R.string.last_name,
        placeholderRes = R.string.last_name
    )

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
}

@Composable
private fun LocationSection(viewModel: EditUserProfileViewModel) {
    AppTextField(
        value = viewModel.country,
        onValueChange = { viewModel.country = it },
        labelRes = R.string.country,
        placeholderRes = R.string.country
    )

    AppTextField(
        value = viewModel.city,
        onValueChange = { viewModel.city = it },
        labelRes = R.string.city,
        placeholderRes = R.string.city
    )

    AppTextField(
        value = viewModel.district,
        onValueChange = { viewModel.district = it },
        labelRes = R.string.district,
        placeholderRes = R.string.district
    )
}

@Composable
private fun NotificationOptions(viewModel: EditUserProfileViewModel) {
    val selectedOptions by viewModel.selectedOptions
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NotificationChip(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.telegram),
                isSelected = selectedOptions.contains(1),
                onToggle = { viewModel.toggleNotificationOption(1) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            NotificationChip(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.email),
                isSelected = selectedOptions.contains(2),
                onToggle = { viewModel.toggleNotificationOption(2) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            NotificationChip(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.mobile_app),
                isSelected = selectedOptions.contains(3),
                onToggle = { viewModel.toggleNotificationOption(3) }
            )
        }
    }
}

@Composable
private fun NotificationChip(
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        onClick = { onToggle() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
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
private fun SaveButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomTheme.colors.orange,
            contentColor = CustomTheme.colors.content
        )
    ) {
        Text(
            text = stringResource(R.string.save_changes),
            style = AppTypography.bodyMedium
        )
    }
}

@Composable
private fun LoadingIndicator(state: UIState) {
    if (state is UIState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}