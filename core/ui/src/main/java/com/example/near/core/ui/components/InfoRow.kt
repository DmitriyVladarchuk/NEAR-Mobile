package com.example.near.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.NEARTheme

/**
 * Универсальный компонент для отображения информации в строке.
 *
 * @param label Основной текст метки (обязательный)
 * @param value Дополнительное значение (опционально)
 * @param icon Иконка (опционально)
 * @param iconSize Размер иконки [IconSize]
 * @param maxLines Максимальное количество строк для текста
 * @param overflow Поведение переполнения текста
 * @param modifier Модификатор для дополнительной настройки
 */
@Composable
fun InfoRow(
    label: String,
    value: String? = null,
    icon: ImageVector? = null,
    iconSize: IconSize = IconSize.SMALL,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        icon?.let {
            AppIcon(
                imageVector = it,
                size = iconSize,
                contentDescription = null
            )
        }

        Text(
            text = if (value != null) "$label:" else label,
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content
        )

        value?.let {
            Text(
                text = it,
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
                fontWeight = FontWeight.Bold,
                maxLines = maxLines,
                overflow = overflow
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoRowPreview() {
    NEARTheme {
        Column(modifier = Modifier.padding(Dimens.spacingMedium).width(250.dp)) {
            InfoRow(
                label = "Простая метка",
                modifier = Modifier.padding(vertical = Dimens.spacingSmall)
            )

            InfoRow(
                label = "Email",
                value = "user@example.com",
                modifier = Modifier.padding(vertical = Dimens.spacingSmall)
            )

            InfoRow(
                label = "Настройки",
                icon = Icons.Default.Settings,
                modifier = Modifier.padding(vertical = Dimens.spacingSmall)
            )

            InfoRow(
                label = "Email",
                value = "user@example.com",
                icon = Icons.Default.Email,
                modifier = Modifier.padding(vertical = Dimens.spacingSmall)
            )

            InfoRow(
                label = "Большая иконка",
                icon = Icons.Default.Settings,
                iconSize = IconSize.MEDIUM,
                modifier = Modifier.padding(vertical = Dimens.spacingSmall)
            )
        }
    }
}