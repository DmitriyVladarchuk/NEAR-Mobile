package com.example.near.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.R
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: Int,
    placeholderRes: Int,
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
        label = { TextFieldLabel(stringResource(labelRes)) },
        placeholder = { TextFieldPlaceholder(stringResource(placeholderRes)) },
        textStyle = AppTypography.bodyMedium,
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        isError = isError,
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    NEARTheme {
        AppTextField(
            value = "Text",
            onValueChange = {  },
            labelRes = R.string.email,
            placeholderRes = R.string.email
        )
    }
}