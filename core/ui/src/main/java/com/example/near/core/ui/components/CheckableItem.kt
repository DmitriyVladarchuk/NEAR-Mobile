package com.example.near.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.NEARTheme

/**
 * Универсальный компонент для элемента с checkbox.
 * Поддерживает различные конфигурации: с иконкой, основным и дополнительным текстом.
 *
 * @param checked Состояние checkbox (выбран/не выбран)
 * @param onCheckedChange Обработчик изменения состояния checkbox
 * @param modifier Модификатор для настройки layout и поведения
 * @param enabled Доступность элемента для взаимодействия
 * @param leadingContent Контент перед checkbox (опционально)
 * @param mainText Основной текст элемента
 * @param secondaryText Дополнительный текст (опционально)
 * @param trailingContent Контент после текста (опционально)
 *
 */
@Composable
fun CheckableItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingContent: @Composable (() -> Unit)? = null,
    mainText: String,
    secondaryText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(horizontal = Dimens.spacingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = CheckboxDefaults.colors(
                checkedColor = CustomTheme.colors.orange,
                uncheckedColor = CustomTheme.colors.content
            ),
        )

        leadingContent?.let {
            it.invoke()
            Spacer(modifier = Modifier.padding(Dimens.spacingExtraSmall))
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingExtraSmall)
        ) {
            Text(
                text = mainText,
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content
            )

            secondaryText?.let { text ->
                Text(
                    text = text,
                    style = AppTypography.bodySmall,
                    color = CustomTheme.colors.caption
                )
            }
        }

        trailingContent?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckableItemPreview() {
    NEARTheme {
        Column {
            CheckableItem(
                checked = false,
                onCheckedChange = {},
                mainText = "Простой элемент"
            )

            CheckableItem(
                checked = true,
                onCheckedChange = {},
                mainText = "Элемент с описанием",
                secondaryText = "Дополнительная информация"
            )

            // С иконкой
            CheckableItem(
                checked = true,
                onCheckedChange = {},
                leadingContent = {
                    AppIcon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Person,
                        size = IconSize.MEDIUM,
                        contentDescription = "Small icon"
                    )
                },
                mainText = "С иконкой",
                secondaryText = "Дополнительный текст",
                modifier = Modifier.padding(vertical = Dimens.spacingSmall)
            )

            CheckableItem(
                checked = true,
                onCheckedChange = {},
                mainText = "Неактивный элемент",
                secondaryText = "Недоступен для выбора",
                enabled = false
            )

            CheckableItem(
                checked = false,
                onCheckedChange = {},
                mainText = "Неактивный элемент",
                secondaryText = "Недоступен для выбора",
                enabled = false
            )
        }
    }
}