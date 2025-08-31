package com.example.near.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.R
import com.example.near.core.network.model.emergencyTypes
import com.example.near.user.models.UserTemplate
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.dark_content

@Composable
fun SendTemplateDialog(
    template: UserTemplate?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CustomTheme.colors.background,
        title = {
            Text(
                text = stringResource(R.string.send_template_title),
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content
            )
        },
        text = {
            Text(
                text = stringResource(R.string.send_template_message, template?.templateName.orEmpty()),
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.colors.orange
                )
            ) {
                Text(
                    text = stringResource(R.string.send),
                    style = AppTypography.bodyMedium,
                    color = dark_content
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.colors.container_2
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = AppTypography.bodyMedium,
                    color = CustomTheme.colors.content
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SendTemplateDialogPreview() {
    NEARTheme {

        val template = UserTemplate(
            id = "2136593275",
            templateName = "Test Template",
            message = "TODO()",
            emergencyType = emergencyTypes[0]
        )
        
        SendTemplateDialog(
            template = template,
            onDismiss = { },
            onConfirm = { },
            modifier = Modifier
        )
    }
}