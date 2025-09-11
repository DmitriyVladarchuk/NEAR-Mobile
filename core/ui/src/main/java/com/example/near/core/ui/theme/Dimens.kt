package com.example.near.core.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.Dimens.spacingMedium

/**
 * Отступы для всего приложения
 */
object Dimens {
    // Базовые отступы
    val spacingNone: Dp = 0.dp
    val spacingExtraSmall: Dp = 4.dp
    val spacingSmall: Dp = 8.dp
    val spacingMedium: Dp = 16.dp
    val spacingLarge: Dp = 24.dp
    val spacingExtraLarge: Dp = 32.dp
    val spacingHuge: Dp = 48.dp

    // Размеры компонентов
    val textFieldHeight: Dp = 56.dp
    val buttonHeight: Dp = 48.dp
    val iconSizeSmall: Dp = 16.dp
    val iconSizeMedium: Dp = 24.dp
    val iconSizeLarge: Dp = 32.dp

    // Радиусы скруглений
    val cornerRadiusSmall: Dp = 4.dp
    val cornerRadiusMedium: Dp = 8.dp
    val cornerRadiusLarge: Dp = 12.dp
    val cornerRadiusExtraLarge: Dp = 24.dp
}

/**
 * Функции-расширения для Modifier с предустановленными отступами
 */
fun Modifier.screenPadding(): Modifier = this.padding(
    horizontal = spacingMedium,
    vertical = spacingMedium
)