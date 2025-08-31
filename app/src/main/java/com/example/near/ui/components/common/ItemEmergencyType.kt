package com.example.near.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.near.core.network.model.EmergencyType
import com.example.near.core.network.model.emergencyTypes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.dark_content

@Composable
fun ItemEmergencyType(emergencyType: EmergencyType, modifier: Modifier = Modifier) {
    val type = emergencyTypes.find { it.id == emergencyType.id }
    Box(
        modifier = modifier
            .padding(vertical = 8.dp)
            .background(
                color = Color(type!!.color.toColorInt()),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emergencyType.title,
            style = AppTypography.bodySmall,
            color = dark_content,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEmergencyTypePreview() {
    NEARTheme {

        val emergencyType = emergencyTypes[0]

        ItemEmergencyType(
            emergencyType = emergencyType,
            modifier = Modifier
        )
    }
}