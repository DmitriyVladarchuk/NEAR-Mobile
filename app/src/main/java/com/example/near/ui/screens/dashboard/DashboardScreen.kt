package com.example.near.ui.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.UserTemplate
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.dark_content
import com.example.near.ui.views.MainHeaderTextInfo

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
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

        // BodyButtons занимает ровно 50% экрана
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            BodyButtons(navController)
        }

        // BodyTemplates занимает оставшееся место
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            BodyTemplates(navController, viewModel.notificationTemplates)
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
                    onClick = { /* обработка клика */ },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                CreateNewGroup(
                    onClick = { /* обработка клика */ },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

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
                SubmittedTemplateButton(
                    onClick = { /* обработка клика */ },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                ButtonSos(
                    onClick = { /* обработка клика */ },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun BodyTemplates(navController: NavController, templates: List<NotificationOption>) {
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
                ItemTemplate(template)
            }
        }
    }
}

@Composable
private fun ItemTemplate(template: NotificationOption) {

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

@Composable
private fun ButtonSos(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CustomTheme.colors.orange,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.sos_top),
                contentDescription = null,
                modifier = Modifier.width(52.dp).height(52.dp),
                tint = dark_content
            )
            Text(
                text = stringResource(R.string.sos),
                style = AppTypography.titleLarge,
                fontSize = 42.sp,
                color = dark_content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.sos_button_description),
                style = AppTypography.bodyMedium,
                color = dark_content,
                modifier = Modifier.weight(1f).padding(top = 10.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}