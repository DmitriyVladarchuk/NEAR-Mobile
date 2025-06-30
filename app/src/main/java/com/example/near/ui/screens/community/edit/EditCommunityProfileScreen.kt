package com.example.near.ui.screens.community.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.shared.models.emergencyTypes
import com.example.near.ui.components.common.AppTextField
import com.example.near.ui.components.common.EmergencyTypeChip
import com.example.near.ui.components.decorations.LoadingIndicator
import com.example.near.ui.components.dialogs.UiStateNotifier
import com.example.near.ui.components.headers.SecondaryHeaderTextInfo
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme


@Composable
fun EditCommunityProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EditCommunityProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    UiStateNotifier(
        state = viewModel.uiState.value,
        context = context,
        successMessage = "Profile updated successfully",
        onSuccess = { navController.popBackStack() },
    )

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(R.string.edit_profile),
            modifier = Modifier.padding(vertical = 16.dp)
        ) { navController.popBackStack() }

        Box(modifier.fillMaxSize()) {
            ProfileForm(viewModel)
            SaveButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = viewModel::submitChanges
            )
            LoadingIndicator(viewModel.uiState.value)
        }
    }

}

@Composable
private fun ProfileForm(viewModel: EditCommunityProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppTextField(
            value = viewModel.communityName,
            onValueChange = { viewModel.communityName = it },
            labelRes = R.string.community_name,
            placeholderRes = R.string.community_name
        )

        AppTextField(
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
            labelRes = R.string.description,
            placeholderRes = R.string.description
        )

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

        Text(
            text = stringResource(R.string.select_emergency_types),
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        EmergencyTypeGrid(
            selectedIds = viewModel.emergencyTypeIds.value,
            onTypeSelected = viewModel::toggleEmergencyType
        )
    }
}

@Composable
private fun EmergencyTypeGrid(
    selectedIds: List<Int>,
    onTypeSelected: (Int) -> Unit
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
                        isSelected = selectedIds.contains(type.id),
                        onSelected = { onTypeSelected(type.id) },
                        modifier = Modifier.weight(1f)
                    )
                }

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