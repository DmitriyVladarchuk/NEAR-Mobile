package com.example.near.ui.screens.profile.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Box(modifier.fillMaxSize()) {
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
        }

        SaveButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { viewModel.submitChanges() }
        )
    }
}

@Composable
private fun ProfileForm(viewModel: EditUserProfileViewModel) {
    Column(Modifier.padding(horizontal = 16.dp)) {
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
        onValueChange = { viewModel.birthday = it },
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
private fun SaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomTheme.colors.container_2,
            contentColor = CustomTheme.colors.content
        )
    ) {
        Text(
            text = stringResource(R.string.save_changes),
            style = AppTypography.bodyMedium
        )
    }
}

private class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString) = TransformedText(text, OffsetMapping.Identity)
}