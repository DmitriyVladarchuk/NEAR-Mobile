package com.example.near.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val lightColors = CustomColors(
    background = light_background,
    container = light_container,
    content = light_content,
    currentContent = current_content,
    currentContainer = current_container,
    orange = orange
)

private val darkColors = CustomColors(
    background = dark_background,
    container = dark_container,
    content = dark_content,
    currentContent = current_content,
    currentContainer = current_container,
    orange = orange
)

val LocalCustomColors = staticCompositionLocalOf<CustomColors> {
    error("Colors composition error")
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomColors.current
}

@Composable
fun NEARTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!darkTheme) lightColors
    else darkColors

    CompositionLocalProvider(
        LocalCustomColors provides colors,
        content = content
    )
}