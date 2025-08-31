package com.example.near.ui.screens.dashboard.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.feature.user.domain.models.UserTemplate
import com.example.near.ui.components.common.ItemTemplate
import com.example.near.ui.components.common.SubmittedTemplateButton
import com.example.near.ui.components.decorations.dashedBorder
import com.example.near.ui.components.dialogs.SendTemplateDialog
import com.example.near.ui.components.dialogs.UiStateNotifier
import com.example.near.ui.components.headers.MainHeaderTextInfo
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme

@Composable
fun DashboardCommunityScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DashboardCommunityViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    UiStateNotifier(
        state = viewModel.uiState.value,
        context = LocalContext.current,
        successMessage = "Send notification is successful"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        MainHeaderTextInfo(
            text = stringResource(R.string.dashboard),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        ) {
            BodyButtons(navController)
        }

        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            BodyTemplates(
                navController,
                viewModel.notificationTemplates,
                viewModel
            )
        }
    }
}

@Composable
private fun BodyButtons(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                CreateNewNotifications(
                    onClick = { navController.navigate(Routes.CreateTemplate.route) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                SubmittedTemplateButton(
                    onClick = { /* обработка клика */ },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun BodyTemplates(
    navController: NavController,
    templates: List<UserTemplate>,
    viewModel: DashboardCommunityViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedTemplate by remember { mutableStateOf<UserTemplate?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.my_templates),
            style = AppTypography.titleLarge,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(templates) { template ->
                ItemTemplate(
                    template = template,
                    onClick = {
                        selectedTemplate = template
                        showDialog = true
                        //viewModel.send(template.id)
                    },
                    onEdit = {
                        navController.navigate("edit_template/${template.id}")
                    },
                    onDelete = {
                        //viewModel.deleteTemplate(template)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (showDialog && selectedTemplate != null) {
        SendTemplateDialog(
            template = selectedTemplate,
            onDismiss = { showDialog = false },
            onConfirm = {
                //viewModel.send(selectedTemplate!!.id)
                showDialog = false
            }
        )
    }
}

@Composable
private fun CreateNewNotifications(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .dashedBorder(color = CustomTheme.colors.content)
            .background(
                color = CustomTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.create_a_new_notification_template),
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
                modifier = Modifier.weight(1f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = CustomTheme.colors.content
            )
        }
    }
}