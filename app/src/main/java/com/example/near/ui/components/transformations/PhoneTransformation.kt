package com.example.near.ui.components.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class RussianPhoneTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Удаляем все нецифры, кроме + в начале
        val cleaned = if (text.text.startsWith("+")) {
            "+" + text.text.substring(1).filter { it.isDigit() }
        } else {
            text.text.filter { it.isDigit() }
        }.take(12) // + и 11 цифр

        val digits = cleaned.drop(1) // Цифры без +

        val formatted = buildString {
            append("+7")
            when {
                digits.isEmpty() -> Unit
                digits.length <= 3 -> append(" (${digits}")
                digits.length <= 6 -> append(" (${digits.take(3)}) ${digits.drop(3)}")
                digits.length <= 8 -> append(" (${digits.take(3)}) ${digits.drop(3).take(3)}-${digits.drop(6)}")
                else -> append(" (${digits.take(3)}) ${digits.drop(3).take(3)}-${digits.drop(6).take(2)}-${digits.drop(8)}")
            }
        }

        return TransformedText(
            AnnotatedString(formatted),
            RussianPhoneOffsetMapping(cleaned.length)
        )
    }
}

class RussianPhoneOffsetMapping(
    private val originalLength: Int
) : OffsetMapping {
    private val maxDigits = 11 // Максимум 11 цифр после +

    override fun originalToTransformed(offset: Int): Int {
        return when {
            offset == 0 -> 0
            offset == 1 -> 2  // После +7
            offset <= 4 -> offset + 3  // После +7 (XXX
            offset <= 7 -> offset + 5  // После +7 (XXX) XXX
            offset <= 9 -> offset + 6  // После +7 (XXX) XXX-XX
            offset <= 11 -> offset + 7 // После +7 (XXX) XXX-XX-XX
            else -> 18 // Максимальная длина
        }.coerceAtMost(18)
    }

    override fun transformedToOriginal(offset: Int): Int {
        return when {
            offset <= 2 -> minOf(offset, 1)  // +7
            offset <= 6 -> minOf(offset - 2, 4)  // +7 (XXX
            offset <= 10 -> minOf(offset - 4, 7) // +7 (XXX) XXX
            offset <= 13 -> minOf(offset - 5, 9) // +7 (XXX) XXX-XX
            else -> minOf(offset - 6, 11) // +7 (XXX) XXX-XX-XX
        }.coerceAtLeast(0)
    }
}