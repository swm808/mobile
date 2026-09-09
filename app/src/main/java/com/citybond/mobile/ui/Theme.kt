package com.citybond.mobile.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF146A60), onPrimary = Color.White,
    primaryContainer = Color(0xFFD9EEE7), onPrimaryContainer = Color(0xFF103F39),
    secondary = Color(0xFF5D746C), background = Color(0xFFF6F8F7),
    surface = Color(0xFFF6F8F7), surfaceContainer = Color.White,
    onSurface = Color(0xFF182E29), onSurfaceVariant = Color(0xFF64766F),
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFF8CD3BE), primaryContainer = Color(0xFF174D42),
    background = Color(0xFF101A16), surface = Color(0xFF101A16),
    surfaceContainer = Color(0xFF1B2822), onSurface = Color(0xFFE0EBE4),
)

@Composable
fun CityBondTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors, content = content)
}
