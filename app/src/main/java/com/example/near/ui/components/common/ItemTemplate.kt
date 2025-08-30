package com.example.near.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.common.models.emergencyTypes
import com.example.near.user.models.UserTemplate
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme

@Composable
fun ItemTemplate(
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

            TemplateOptionsMenu(
                expanded = expanded.value,
                onDismiss = { expanded.value = false },
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun TemplateOptionsMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = CustomTheme.colors.background
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.edit),
                    style = AppTypography.bodyMedium,
                    color = CustomTheme.colors.content
                )
            },
            onClick = {
                onDismiss()
                onEdit()
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.delete),
                    style = AppTypography.bodyMedium,
                    color = CustomTheme.colors.content
                )
            },
            onClick = {
                onDismiss()
                onDelete()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemTemplatePreview() {
    NEARTheme {

        val template = UserTemplate(
            id = "2136593275",
            templateName = "Test Template",
            message = "TODO()",
            emergencyType = emergencyTypes[0]
        )

        ItemTemplate(
            template = template,
            onClick = { TODO() },
            onEdit = { TODO() },
            onDelete = { TODO() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TemplateOptionsMenuPreview() {
    NEARTheme {

        TemplateOptionsMenu(
            expanded = true,
            onDismiss = {  },
            onEdit = {  },
            onDelete = {  }
        )
    }
}
