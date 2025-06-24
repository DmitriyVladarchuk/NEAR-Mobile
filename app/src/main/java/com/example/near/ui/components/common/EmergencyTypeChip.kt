package com.example.near.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.domain.shared.models.emergencyTypes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.dark_content

@Composable
fun EmergencyTypeChip(
    type: EmergencyType,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = Color(type.color.toColorInt())

    FilterChip(
        selected = isSelected,
        onClick = onSelected,
        label = {
            Text(
                text = type.title,
                style = AppTypography.bodySmall,
                color = if (isSelected) dark_content else textColor
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = textColor,
            containerColor = CustomTheme.colors.container_2,
            selectedLabelColor = CustomTheme.colors.background,
            labelColor = textColor
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun EmergencyTypeChipPreview() {
    NEARTheme {
        val emergencyType = emergencyTypes[0]

        Column {

            EmergencyTypeChip(
                type = emergencyType,
                isSelected = false,
                onSelected = { TODO() },
                modifier = Modifier
            )

            EmergencyTypeChip(
                type = emergencyType,
                isSelected = true,
                onSelected = { TODO() },
                modifier = Modifier
            )
        }
    }
}