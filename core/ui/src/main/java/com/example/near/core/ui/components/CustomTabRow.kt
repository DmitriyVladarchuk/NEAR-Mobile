package com.example.near.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.NEARTheme

/**
 * Кастомная реализация TabRow с закругленными табами.
 * Альтернатива стандартному TabRow с индикатором.
 *
 * @param tabs Список заголовков табов
 * @param selectedTabIndex Индекс выбранного таба
 * @param onTabSelected Обработчик выбора таба
 * @param modifier Модификатор для настройки контейнера
 *
 * @sample CustomTabRowPreview
 */
@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
            .background(CustomTheme.colors.container_2),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingNone)
    ) {
        tabs.forEachIndexed { index, tab ->
            TabItem(
                text = tab,
                isSelected = index == selectedTabIndex,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTabRowPreview() {
    NEARTheme {
        Column(modifier = Modifier.padding(Dimens.spacingMedium)) {
            CustomTabRow(
                tabs = listOf("Друзья", "Группы"),
                selectedTabIndex = 0,
                onTabSelected = {}
            )

            CustomTabRow(
                tabs = listOf("Все", "Онлайн", "Оффлайн"),
                selectedTabIndex = 1,
                onTabSelected = {},
                modifier = Modifier.padding(top = Dimens.spacingLarge)
            )

            CustomTabRow(
                tabs = listOf("Янв", "Фев", "Мар", "Апр"),
                selectedTabIndex = 0,
                onTabSelected = {},
                modifier = Modifier.padding(top = Dimens.spacingLarge)
            )
        }
    }
}