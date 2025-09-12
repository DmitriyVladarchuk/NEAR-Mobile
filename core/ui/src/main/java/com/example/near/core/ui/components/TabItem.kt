package com.example.near.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens

/**
 * Элемент таба для использования в CustomTabRow.
 *
 * @param text Текст таба
 * @param isSelected Флаг выбранного состояния
 * @param modifier Модификатор для настройки элемента
 * @param onClick Обработчик клика по табу
 */
@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        CustomTheme.colors.orange
    } else {
        Color.Transparent
    }

    val textColor = if (isSelected) {
        CustomTheme.colors.background
    } else {
        CustomTheme.colors.content
    }

    Tab(
        selected = isSelected,
        onClick = onClick,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
            ),
        content = {
            Text(
                text = text,
                style = AppTypography.bodyMedium,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        vertical = Dimens.spacingSmall,
                        horizontal = Dimens.spacingMedium
                    )
            )
        }
    )
}