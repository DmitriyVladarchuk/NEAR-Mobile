package com.example.near.ui.components.decorations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.near.core.ui.theme.CustomTheme

@Composable
fun <T> DynamicItemContainer(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T, Boolean, Modifier, (T) -> Unit) -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CustomTheme.colors.container_2),
    ) {
        items.forEach { item ->
            val isSelected = item == selectedItem
            itemContent(item, isSelected, Modifier.weight(1f)) { selectedItem ->
                onItemSelected(selectedItem)
            }
        }
    }
}