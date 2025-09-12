package com.example.near.ui.screens.templates.create

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.near.core.network.model.EmergencyType
import com.example.near.core.network.model.emergencyTypes
import com.example.near.core.ui.components.AppTextField
import com.example.near.core.ui.components.CategoryChip
import com.example.near.core.ui.components.ChipSize
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.screenPadding
import com.example.near.ui.components.headers.SecondaryHeaderTextInfo

@Composable
fun CreateTemplateScreen(
    templateId: String? = null,
    navController: NavController,
) {
    val viewModel: CreateTemplateViewModel = hiltViewModel()

    CreateTemplateScreen(
        templateId = templateId,
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
private fun CreateTemplateScreen(
    templateId: String? = null,
    navController: NavController,
    viewModel: CreateTemplateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showEmergencyTypeDialog by remember { mutableStateOf(false) }

    if (showEmergencyTypeDialog) {
        EmergencyTypeDialog(
            onDismiss = { showEmergencyTypeDialog = false },
            onSelect = { type ->
                viewModel.processEvent(TemplateEvent.SelectEmergencyType(type))
                showEmergencyTypeDialog = false
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.processEvent(TemplateEvent.LoadTemplate(templateId))
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .screenPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SecondaryHeaderTextInfo(
            text = if (templateId == null) stringResource(R.string.create_template)
            else stringResource(R.string.update_template),
            onClick = { navController.popBackStack() }
        )

        AppTextField(
            value = state.templateName,
            onValueChange = { viewModel.processEvent(TemplateEvent.UpdateTemplateName(it)) },
            labelRes = stringResource(R.string.template_name),
            placeholderRes = stringResource(R.string.template_name),
            modifier = Modifier.padding(bottom = Dimens.spacingMedium),
            isError = state.validationErrors.contains(Field.TEMPLATE_NAME),
            errorMessage = stringResource(R.string.error_name)
        )

        AppTextField(
            value = state.message,
            onValueChange = { viewModel.processEvent(TemplateEvent.UpdateMessage(it)) },
            labelRes = stringResource(R.string.message),
            placeholderRes = stringResource(R.string.message),
            modifier = Modifier.padding(bottom = Dimens.spacingMedium),
            isError = state.validationErrors.contains(Field.MESSAGE),
            errorMessage = stringResource(R.string.error_message),
            singleLine = false,
            maxLines = 5,
            minLines = 3
        )

        EmergencyTypeSelector(
            state = state,
            onShowDialog = { showEmergencyTypeDialog = true },
            modifier = Modifier.padding(bottom = Dimens.spacingSmall)
        )

        Button(
            onClick = {
                viewModel.processEvent(TemplateEvent.SaveTemplate(templateId))
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.cornerRadiusLarge),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.orange,
                contentColor = Color.White,
                disabledContainerColor = CustomTheme.colors.orange.copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ),
            enabled = state.templateName.isNotBlank() &&
                    state.message.isNotBlank() &&
                    state.selectedEmergencyType != null &&
                    !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = if (templateId == null) stringResource(R.string.save_template)
                    else stringResource(R.string.update_template),
                    style = AppTypography.bodyMedium,
                    modifier = Modifier.padding(vertical = Dimens.spacingSmall)
                )
            }
        }
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
                    CategoryChip(
                        title = type.title,
                        backgroundColor = Color(type.color.toColorInt()),
                        size = ChipSize.MEDIUM,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelect(type)
                                onDismiss()
                            }
                            .padding(vertical = Dimens.spacingSmall)
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
private fun EmergencyTypeSelector(
    state: TemplateState,
    onShowDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Button(
            onClick = onShowDialog,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.container_2
            ),
            shape = RoundedCornerShape(Dimens.cornerRadiusLarge)
        ) {
            Text(
                text = state.selectedEmergencyType?.title
                    ?: stringResource(R.string.select_emergency_type),
                color = state.selectedEmergencyType?.let {
                    Color(it.color.toColorInt())
                } ?: CustomTheme.colors.content,
                style = AppTypography.bodyMedium
            )
        }

        if (state.validationErrors.contains(Field.EMERGENCY_TYPE)) {
            Text(
                text = stringResource(R.string.error_emergency_type),
                color = CustomTheme.colors.orange,
                style = AppTypography.labelSmall,
                modifier = Modifier
                    .padding(bottom = Dimens.spacingMedium)
                    .fillMaxWidth()
            )
        } else {
            Box(modifier = Modifier.padding(bottom = Dimens.spacingMedium))
        }
    }
}