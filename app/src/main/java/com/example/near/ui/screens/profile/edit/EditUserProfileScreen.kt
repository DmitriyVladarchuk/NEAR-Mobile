package com.example.near.ui.screens.profile.edit

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.common.UIState
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.SecondaryHeaderTextInfo
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder
import com.example.near.ui.views.textFieldColors

@Composable
fun EditUserProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EditUserProfileViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState
    val context = LocalContext.current

    HandleUiState(uiState, navController, context)

    Box(modifier.fillMaxSize().padding(16.dp)) {
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
private fun HandleUiState(
    state: UIState,
    navController: NavController,
    context: Context
) {
    LaunchedEffect(state) {
        when (state) {
            is UIState.Success -> {
                navController.popBackStack()
                Toast.makeText(
                    context,
                    "Profile updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is UIState.Error -> {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
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
    EditProfileField(
        value = viewModel.firstName,
        onValueChange = { viewModel.firstName = it },
        labelRes = R.string.first_name
    )

    EditProfileField(
        value = viewModel.lastName,
        onValueChange = { viewModel.lastName = it },
        labelRes = R.string.last_name
    )

    EditProfileField(
        value = viewModel.birthday,
        onValueChange = { newText ->
            val filtered = newText.filter { it.isDigit() }.take(8)
            viewModel.birthday = filtered
        },
        labelRes = R.string.birthday,
        keyboardType = KeyboardType.Number,
        visualTransformation = DateTransformation()
    )
}

@Composable
private fun LocationSection(viewModel: EditUserProfileViewModel) {
    EditProfileField(
        value = viewModel.country,
        onValueChange = { viewModel.country = it },
        labelRes = R.string.country
    )

    EditProfileField(
        value = viewModel.city,
        onValueChange = { viewModel.city = it },
        labelRes = R.string.city
    )

    EditProfileField(
        value = viewModel.district,
        onValueChange = { viewModel.district = it },
        labelRes = R.string.district
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
private fun EditProfileField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { TextFieldLabel(stringResource(labelRes)) },
        placeholder = { TextFieldPlaceholder(stringResource(labelRes)) },
        textStyle = AppTypography.bodyMedium,
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )
    Spacer(Modifier.height(16.dp))
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

private class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(8)

        val formatted = buildString {
            digits.forEachIndexed { index, char ->
                when (index) {
                    4 -> append('-')
                    6 -> append('-')
                }
                append(char)
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = DateOffsetMapping
        )
    }
}

private object DateOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return when {
            offset <= 4 -> offset
            offset <= 6 -> offset + 1
            offset <= 8 -> offset + 2
            else -> 10
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        return when {
            offset <= 4 -> offset
            offset <= 7 -> offset - 1
            offset <= 10 -> offset - 2
            else -> 8
        }
    }
}