package com.example.near.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.NEARTheme
import com.example.near.core.ui.theme.dark_content
import com.example.near.core.ui.theme.light_content

/**
 * Размеры чипа для унификации внешнего вида в приложении.
 *
 * @property SMALL Компактный размер
 * @property MEDIUM Стандартный размер
 * @property LARGE Крупный размер
 */
enum class ChipSize {
    SMALL,
    MEDIUM,
    LARGE
}

/**
 * Универсальный компонент для отображения категорий/типов в виде чипа
 * Компонент автоматически подбирает контрастный цвет текста на основе переданного фона,
 * используя цвета из темы приложения.
 *
 * @param title Текст для отображения
 * @param backgroundColor Цвет фона чипа
 * @param textColor Цвет текста (опционально, будет выбран автоматически если null)
 * @param size Размер чипа для унификации отступов
 * @param modifier Модификатор для дополнительной настройки
 */
@Composable
fun CategoryChip(
    title: String,
    backgroundColor: Color,
    textColor: Color? = null,
    size: ChipSize = ChipSize.MEDIUM,
    modifier: Modifier = Modifier
) {
    val calculatedTextColor = textColor ?: getContrastTextColor(backgroundColor)
    val (verticalPadding, horizontalPadding, textStyle) = when (size) {
        ChipSize.SMALL -> Triple(
            Dimens.spacingExtraSmall,
            Dimens.spacingSmall,
            AppTypography.labelSmall
        )
        ChipSize.MEDIUM -> Triple(
            Dimens.spacingSmall,
            Dimens.spacingMedium,
            AppTypography.bodySmall
        )
        ChipSize.LARGE -> Triple(
            Dimens.spacingSmall,
            Dimens.spacingLarge,
            AppTypography.bodyMedium
        )
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = textStyle,
            color = calculatedTextColor,
            modifier = Modifier.padding(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            )
        )
    }
}

/**
 * Определяет контрастный цвет текста на основе фона
 */
private fun getContrastTextColor(backgroundColor: Color): Color {
    val luminance = 0.299 * backgroundColor.red +
            0.587 * backgroundColor.green +
            0.114 * backgroundColor.blue
    return if (luminance > 0.5) light_content else dark_content
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipPreview() {
    NEARTheme {
        Column(modifier = Modifier.padding(Dimens.spacingMedium)) {
            // Small size
            CategoryChip(
                title = "Медицинская",
                backgroundColor = Color(0xFFFF6B6B),
                size = ChipSize.SMALL,
                modifier = Modifier.padding(bottom = Dimens.spacingSmall)
            )

            // Medium size (default)
            CategoryChip(
                title = "Медицинская",
                backgroundColor = Color(0xFFFFA726),
                size = ChipSize.MEDIUM,
                modifier = Modifier.padding(bottom = Dimens.spacingSmall)
            )

            // Large size
            CategoryChip(
                title = "Медицинская",
                backgroundColor = Color(0xFF3F51B5),
                size = ChipSize.LARGE
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipWithCustomColorsPreview() {
    NEARTheme {
        Column(modifier = Modifier.padding(Dimens.spacingMedium)) {
            CategoryChip(
                title = "Светлый фон",
                backgroundColor = Color(0xFFE0E0E0),
                size = ChipSize.MEDIUM,
                modifier = Modifier.padding(bottom = Dimens.spacingSmall)
            )

            CategoryChip(
                title = "Темный фон",
                backgroundColor = Color(0xFF2C3E50),
                textColor = CustomTheme.colors.orange,
                size = ChipSize.MEDIUM
            )
        }
    }
}