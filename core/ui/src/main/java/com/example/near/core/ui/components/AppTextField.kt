package com.example.near.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

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
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { TextFieldLabel(labelRes) },
        placeholder = { TextFieldPlaceholder(placeholderRes) },
        textStyle = AppTypography.bodyMedium,
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        isError = isError,
    )
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
    style: TextStyle = AppTypography.bodySmall
) {
    Text(
        text = text,
        style = style,
        color = CustomTheme.colors.container
    )
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CustomTheme.colors.content,
    unfocusedTextColor = CustomTheme.colors.content,
    focusedContainerColor = CustomTheme.colors.background,
    unfocusedContainerColor = CustomTheme.colors.background,
    unfocusedLabelColor = CustomTheme.colors.background,
    cursorColor = CustomTheme.colors.currentContainer,
    focusedBorderColor = CustomTheme.colors.currentContainer,
    unfocusedBorderColor = CustomTheme.colors.container
)

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    NEARTheme {
        AppTextField(
            value = "Text",
            onValueChange = {  },
            labelRes = "email",
            placeholderRes = "email"
        )
    }
}