package com.example.near.ui.screens.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.models.emergencyTypes
import com.example.near.ui.views.SecondaryHeaderTextInfo
import com.example.near.ui.views.textFieldColors

@Composable
fun CreateTemplate(
    templateId: String? = null,
    isCommunity: Boolean = false,
    navController: NavController,
    viewModel: CreateTemplateViewModel = hiltViewModel()
) {
    var showEmergencyTypeDialog by remember { mutableStateOf(false) }

    // --- Загрузка шаблона при редактировании ---
    LaunchedEffect(templateId) {
        viewModel.loadTemplate(templateId, isCommunity)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(
                if (templateId != null) R.string.update_template else R.string.create_template
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            navController.popBackStack()
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = viewModel.templateName,
            onValueChange = viewModel::updateTemplateName,
            label = { Text(stringResource(R.string.template_name)) },
            placeholder = { Text("Earthquake in USA") },
            colors = textFieldColors()
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = viewModel.message,
            onValueChange = viewModel::updateMessage,
            label = { Text(stringResource(R.string.message)) },
            placeholder = { Text("Attention, in 1 hour there will be...") },
            colors = textFieldColors(),
            maxLines = 5
        )

        Button(
            onClick = { showEmergencyTypeDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = viewModel.selectedEmergencyType?.title ?: stringResource(R.string.select_emergency_type),
                color = viewModel.selectedEmergencyType?.let {
                    Color(it.color.toColorInt())
                } ?: MaterialTheme.colorScheme.onSurface
            )
        }

        Button(
            onClick = {
                viewModel.saveTemplate(templateId)
                { navController.popBackStack() } },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.templateName.isNotBlank() &&
                    viewModel.message.isNotBlank() &&
                    viewModel.selectedEmergencyType != null &&
                    !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(stringResource(R.string.save_template))
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
        title = { Text(stringResource(R.string.select_emergency_type)) },
        text = {
            LazyColumn {
                items(emergencyTypes) { type ->
                    ListItem(
                        headlineContent = { Text(type.title) },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        Color(type.bgColor.toColorInt()),
                                        CircleShape
                                    )
                                    .border(
                                        1.dp,
                                        Color(type.color.toColorInt()),
                                        CircleShape
                                    )
                            )
                        },
                        modifier = Modifier
                            .clickable { onSelect(type) }
                            .padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}