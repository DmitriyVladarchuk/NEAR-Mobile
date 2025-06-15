package com.example.near.ui.screens.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.models.common.emergencyTypes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.dark_content
import com.example.near.ui.views.SecondaryHeaderTextInfo
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder
import com.example.near.ui.views.textFieldColors

@Composable
fun CreateTemplate(
    templateId: String? = null,
    isCommunity: Boolean = false,
    navController: NavController,
    viewModel: CreateTemplateViewModel = hiltViewModel()
) {
    var showEmergencyTypeDialog by remember { mutableStateOf(false) }
    val isEditing = templateId != null

    // --- Загрузка шаблона при редактировании ---
    LaunchedEffect(templateId) {
        if (isEditing) {
            viewModel.loadTemplate(templateId, isCommunity)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SecondaryHeaderTextInfo(
            text = if (templateId == null) stringResource(R.string.create_template) else stringResource(R.string.update_template),
            modifier = Modifier.padding(vertical = 16.dp),
            onClick = { navController.popBackStack() }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = viewModel.templateName,
            onValueChange = viewModel::updateTemplateName,
            label = { TextFieldLabel(stringResource(R.string.template_name)) },
            placeholder = { TextFieldPlaceholder("Earthquake in USA") },
            colors = textFieldColors()
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = viewModel.message,
            onValueChange = viewModel::updateMessage,
            label = { TextFieldLabel(stringResource(R.string.message)) },
            placeholder = { TextFieldPlaceholder("Attention, in 1 hour there will be...") },
            colors = textFieldColors(),
            maxLines = 5
        )

        Button(
            onClick = { showEmergencyTypeDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.container_2
            )
        ) {
            Text(
                text = viewModel.selectedEmergencyType?.title
                    ?: stringResource(R.string.select_emergency_type),
                color = viewModel.selectedEmergencyType?.let {
                    Color(it.color.toColorInt())
                } ?: CustomTheme.colors.content
            )
        }

        Button(
            onClick = {
                viewModel.saveTemplate(templateId, isCommunity) {
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.orange,
                contentColor = Color.White
            ),
            enabled = viewModel.templateName.isNotBlank() &&
                    viewModel.message.isNotBlank() &&
                    viewModel.selectedEmergencyType != null &&
                    !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(
                    text = if (isEditing) stringResource(R.string.update_template)
                    else stringResource(R.string.save_template),
                    style = AppTypography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }

    if (showEmergencyTypeDialog) {
        EmergencyTypeDialog(
            onDismiss = { showEmergencyTypeDialog = false },
            onSelect = viewModel::selectEmergencyType
        )
    }
}

@Composable
private fun EmergencyTypeDialog(
    onDismiss: () -> Unit,
    onSelect: (EmergencyType) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.select_emergency_type),
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content
            )
        },
        containerColor = CustomTheme.colors.container_2,
        text = {
            LazyColumn {
                items(emergencyTypes) { type ->
                    ItemEmergencyType(
                        emergencyType = type,
                        modifier = Modifier.clickable {
                            onSelect(type)
                            onDismiss()
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = AppTypography.bodyMedium,
                    color = CustomTheme.colors.content
                )
            }
        }
    )
}

@Composable
private fun ItemEmergencyType(emergencyType: EmergencyType, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .background(
                color = Color(emergencyType.color.toColorInt()),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emergencyType.title,
            style = AppTypography.bodyMedium,
            color = dark_content,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}