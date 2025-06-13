package com.example.near.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.domain.models.common.ThemeType
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.content_inscription
import com.example.near.ui.views.SecondaryHeaderTextInfo

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(R.string.settings),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            navController.popBackStack()
        }
        OptionCard(viewModel)
        Feedback(clickable = {  })
        Common(clickableAbout = {  }, clickableGitHub = {  })
    }
}

@Composable
private fun OptionCard(viewModel: SettingsViewModel) {
    val currentTheme by viewModel.currentTheme.collectAsState(ThemeType.SYSTEM)

    Column(
        modifier = Modifier
            .background(CustomTheme.colors.container_2, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.theme_mode),
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content
        )

        ThemeOption(
            text = stringResource(R.string.light),
            isSelected = currentTheme == ThemeType.LIGHT,
            onClick = { viewModel.setTheme(ThemeType.LIGHT) }
        )

        ThemeOption(
            text = stringResource(R.string.dark),
            isSelected = currentTheme == ThemeType.DARK,
            onClick = { viewModel.setTheme(ThemeType.DARK) }
        )

        ThemeOption(
            text = stringResource(R.string.system),
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
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonColors(
                selectedColor = CustomTheme.colors.currentContainer,
                unselectedColor = CustomTheme.colors.content,
                disabledSelectedColor = CustomTheme.colors.content,
                disabledUnselectedColor = CustomTheme.colors.currentContainer,
            )
        )
        Text(
            text = text,
            style = AppTypography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp),
            color = CustomTheme.colors.content
        )
    }
}

@Composable
private fun Feedback(clickable: () -> Unit) {
    Column(
        modifier = Modifier
            .background(CustomTheme.colors.container_2, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.feedback),
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { clickable() }
        ) {
            Text(
                text = stringResource(id = R.string.report_an_issue),
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content
            )

            Text(
                text = stringResource(id = R.string.report_description),
                style = AppTypography.bodySmall,
                color = content_inscription,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
private fun Common(clickableAbout: () -> Unit, clickableGitHub: () -> Unit) {

    Column(
        modifier = Modifier
            .background(CustomTheme.colors.container_2, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.common),
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content
        )

        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clickable { clickableAbout() }
        ) {
            Text(
                text = stringResource(id = R.string.about_app),
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content
            )

            Text(
                text = stringResource(id = R.string.about_description),
                style = AppTypography.bodySmall,
                color = content_inscription,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { clickableGitHub() }
        ) {
            Text(
                text = stringResource(id = R.string.github),
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content
            )

            Text(
                text = stringResource(id = R.string.github_description),
                style = AppTypography.bodySmall,
                color = content_inscription,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}