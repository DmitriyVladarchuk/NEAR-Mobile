package com.example.near.ui.screens.dashboard.community

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.models.UserTemplate
import com.example.near.ui.screens.dashboard.user.DashboardViewModel
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.dark_content
import com.example.near.ui.views.MainHeaderTextInfo

@Composable
fun DashboardCommunityScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DashboardCommunityViewModel = hiltViewModel()
) {
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

@SuppressLint("SuspiciousModifierThen")
fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp = 8.dp,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 5.dp,
    gapWidth: Dp = 3.dp
) = this.then(
    drawWithCache {
        val strokeWidthPx = strokeWidth.toPx()
        val dashWidthPx = dashWidth.toPx()
        val gapWidthPx = gapWidth.toPx()

        onDrawWithContent {
            drawContent()
            drawRoundRect(
                color = color,
                style = Stroke(
                    width = strokeWidthPx,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(dashWidthPx, gapWidthPx),
                        phase = 0f
                    )
                ),
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
)

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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Send Template",
                    style = AppTypography.titleMedium
                )
            },
            text = {
                Text(
                    text = "Do you want to send this template: ${selectedTemplate?.templateName}?",
                    style = AppTypography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedTemplate?.let { template ->
                            viewModel.send(template.id!!)
                            showDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.container_2
                    )
                ) {
                    Text("Send")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.container_2
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ItemTemplate(
    template: UserTemplate,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = template.templateName,
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content
            )
            ItemEmergencyType(template.emergencyType)
        }

        Box {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "More options",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { expanded.value = true },
                tint = CustomTheme.colors.content
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {
                        expanded.value = false
                        onEdit()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        expanded.value = false
                        onDelete()
                    }
                )
            }
        }
    }
}

@Composable
private fun ItemEmergencyType(emergencyType: EmergencyType, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 8.dp)
            .background(
                color = Color(emergencyType.color.toColorInt()),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emergencyType.title,
            style = AppTypography.bodySmall,
            color = dark_content,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
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

@Composable
private fun CreateNewGroup(
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
                text = stringResource(R.string.create_new_group),
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = CustomTheme.colors.content
            )
        }
    }
}

@Composable
private fun SubmittedTemplateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.submitted_templates),
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.list),
                contentDescription = null,
                modifier = Modifier.weight(1f),
                tint = CustomTheme.colors.content
            )
        }
    }
}