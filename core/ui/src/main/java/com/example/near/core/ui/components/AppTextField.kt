package com.example.near.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.NEARTheme
import com.example.near.core.ui.theme.screenPadding

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: String,
    placeholderRes: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    readOnly: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { TextFieldLabel(text = labelRes, isError = isError) },
            placeholder = { TextFieldPlaceholder(text = placeholderRes) },
            textStyle = AppTypography.bodyMedium.copy(color = CustomTheme.colors.content),
            colors = textFieldColors(isError),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            readOnly = readOnly,
            interactionSource = interactionSource
        )

        errorMessage?.takeIf { isError }?.let { message ->
            Text(
                text = message,
                style = AppTypography.labelSmall,
                color = CustomTheme.colors.orange,
                modifier = Modifier.padding(top = Dimens.spacingExtraSmall)
            )
        }
    }
}

@Composable
fun TextFieldPlaceholder(
    text: String,
    style: TextStyle = AppTypography.bodyMedium
) {
    Text(
        text = text,
        style = style,
        color = CustomTheme.colors.container
    )
}

@Composable
fun TextFieldLabel(
    text: String,
    isError: Boolean = false,
    style: TextStyle = AppTypography.bodySmall
) {
    val color = if (isError) {
        Color.Red
    } else {
        CustomTheme.colors.container
    }

    Text(
        text = text,
        style = style,
        color = color
    )
}

@Composable
fun textFieldColors(isError: Boolean) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CustomTheme.colors.content,
    unfocusedTextColor = CustomTheme.colors.content,
    focusedContainerColor = CustomTheme.colors.background,
    unfocusedContainerColor = CustomTheme.colors.background,
    unfocusedLabelColor = CustomTheme.colors.background,
    cursorColor = CustomTheme.colors.currentContainer,
    focusedBorderColor = if (isError) CustomTheme.colors.error else CustomTheme.colors.currentContainer,
    unfocusedBorderColor = if (isError) CustomTheme.colors.error else CustomTheme.colors.container,
    errorBorderColor = CustomTheme.colors.error,
)

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    NEARTheme {
        Column(
            modifier = Modifier
                .screenPadding()
                .width(300.dp)
        ) {
            AppTextField(
                value = "John Doe",
                onValueChange = {},
                labelRes = "Full Name",
                placeholderRes = "Enter your name"
            )

            AppTextField(
                value = "",
                onValueChange = {},
                labelRes = "Email",
                placeholderRes = "Enter your email",
                modifier = Modifier.padding(top = Dimens.spacingExtraSmall)
            )

            AppTextField(
                value = "invalid-email",
                onValueChange = {},
                labelRes = "Email",
                placeholderRes = "Enter valid email",
                isError = true,
                errorMessage = "Please enter a valid email address",
                modifier = Modifier.padding(top = Dimens.spacingExtraSmall)
            )
        }
    }
}
