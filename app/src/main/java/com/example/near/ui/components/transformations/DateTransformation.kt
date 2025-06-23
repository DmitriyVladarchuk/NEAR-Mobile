package com.example.near.ui.components.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(8)

        val formatted = buildString {
            digits.forEachIndexed { index, char ->
                when (index) {
                    4 -> append('-')
                    6 -> append('-')
                }
                append(char)
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = DateOffsetMapping
        )
    }
}