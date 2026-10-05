package com.citybond.mobile.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF174EA6),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8F0FE),
    onPrimaryContainer = Color(0xFF12366B),
    secondary = Color(0xFF42526B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDF1F7),
    onSecondaryContainer = Color(0xFF25324A),
    tertiary = Color(0xFF087F72),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD9F3EE),
    onTertiaryContainer = Color(0xFF075E56),
    background = Color(0xFFF4F7FB),
    onBackground = Color(0xFF111827),
    surface = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFBFCFE),
    surfaceContainer = Color(0xFFF2F5F9),
    surfaceContainerHigh = Color(0xFFEBF0F6),
    onSurface = Color(0xFF111827),
    onSurfaceVariant = Color(0xFF5F6B7C),
    outline = Color(0xFFC9D3E1),
    outlineVariant = Color(0xFFE3E9F1),
    error = Color(0xFFDC2626),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8CB4FF),
    onPrimary = Color(0xFF082C71),
    primaryContainer = Color(0xFF173B82),
    onPrimaryContainer = Color(0xFFD8E6FF),
    secondary = Color(0xFFBAC6D8),
    secondaryContainer = Color(0xFF273446),
    onSecondaryContainer = Color(0xFFDCE5F4),
    tertiary = Color(0xFF63D6C5),
    onTertiary = Color(0xFF003731),
    tertiaryContainer = Color(0xFF075E56),
    onTertiaryContainer = Color(0xFFD9F3EE),
    background = Color(0xFF0D1420),
    onBackground = Color(0xFFE7EDF7),
    surface = Color(0xFF121B29),
    surfaceContainerLow = Color(0xFF172131),
    surfaceContainer = Color(0xFF1C2737),
    surfaceContainerHigh = Color(0xFF243246),
    onSurface = Color(0xFFE7EDF7),
    onSurfaceVariant = Color(0xFFAAB7C8),
    outline = Color(0xFF3A495E),
    outlineVariant = Color(0xFF29374A),
)

private val CityBondShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

private val CityBondTypography = Typography(
    displaySmall = TextStyle(
        fontSize = 34.sp,
        lineHeight = 42.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.7).sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.4).sp,
    ),
    headlineSmall = TextStyle(
        fontSize = 23.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.2).sp,
    ),
    titleLarge = TextStyle(fontSize = 20.sp, lineHeight = 27.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = TextStyle(fontSize = 16.sp, lineHeight = 23.sp, fontWeight = FontWeight.SemiBold),
    titleSmall = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 21.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 18.sp),
    labelLarge = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold),
    labelMedium = TextStyle(fontSize = 12.sp, lineHeight = 17.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 10.sp, lineHeight = 14.sp, fontWeight = FontWeight.SemiBold),
)

@Composable
fun CityBondTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = CityBondTypography,
        shapes = CityBondShapes,
        content = content,
    )
}
