package com.example.staticmusicui.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.DarkGray,
    secondary = Color.DarkGray,
    tertiary = Color.DarkGray,
    background = Color(0xFF1A1A1A), // Dark gray background
    surface = Color(0xFF281414), // Dark gray surface
    onBackground = Color.LightGray // White text/icons on dark gray
)

@Composable
fun StaticMusicUITheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}