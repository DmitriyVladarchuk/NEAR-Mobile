package com.example.near.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.near.domain.models.ThemeType

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {

    val currentTheme by viewModel.currentTheme.collectAsState(ThemeType.SYSTEM)

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Выберите тему:", style = MaterialTheme.typography.titleMedium)

        ThemeOption(
            text = "Светлая",
            isSelected = currentTheme == ThemeType.LIGHT,
            onClick = { viewModel.setTheme(ThemeType.LIGHT) }
        )

        ThemeOption(
            text = "Тёмная",
            isSelected = currentTheme == ThemeType.DARK,
            onClick = { viewModel.setTheme(ThemeType.DARK) }
        )

        ThemeOption(
            text = "Как в системе",
            isSelected = currentTheme == ThemeType.SYSTEM,
            onClick = { viewModel.setTheme(ThemeType.SYSTEM) }
        )
    }
}

@Composable
private fun ThemeOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Text(text, modifier = Modifier.padding(start = 8.dp))
    }
}