package com.example.near.ui.components.transformations

import androidx.compose.ui.text.input.OffsetMapping

object DateOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return when {
            offset <= 4 -> offset
            offset <= 6 -> offset + 1
            offset <= 8 -> offset + 2
            else -> 10
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        return when {
            offset <= 4 -> offset
            offset <= 7 -> offset - 1
            offset <= 10 -> offset - 2
            else -> 8
        }
    }
}