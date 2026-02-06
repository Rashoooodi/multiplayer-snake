package com.duoglass.launcher

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * DuoGlass Theme Engine
 * Dynamic MaterialTheme wrapper with:
 * - Font selection (Poppins/Outfit)
 * - Theme mode selection (Dark/Light/OLED)
 */

// Color schemes
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF004A77),
    onPrimaryContainer = Color(0xFFCAE6FF),
    secondary = Color(0xFFBCC7DC),
    onSecondary = Color(0xFF263141),
    secondaryContainer = Color(0xFF3C4758),
    onSecondaryContainer = Color(0xFFD8E3F8),
    tertiary = Color(0xFFD8BFD8),
    onTertiary = Color(0xFF3E2844),
    tertiaryContainer = Color(0xFF553E5C),
    onTertiaryContainer = Color(0xFFF5DBF4),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1A1C1E),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF1A1C1E),
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF42474E),
    onSurfaceVariant = Color(0xFFC2C7CE),
    outline = Color(0xFF8C9199),
    outlineVariant = Color(0xFF42474E),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF005FA8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD2E4FF),
    onPrimaryContainer = Color(0xFF001C38),
    secondary = Color(0xFF545F71),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD8E3F8),
    onSecondaryContainer = Color(0xFF111C2B),
    tertiary = Color(0xFF6D5677),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF5DBF4),
    onTertiaryContainer = Color(0xFF271431),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFDFCFF),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFDFCFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFDFE2EB),
    onSurfaceVariant = Color(0xFF42474E),
    outline = Color(0xFF73777F),
    outlineVariant = Color(0xFFC2C7CE),
)

private val OledColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF004A77),
    onPrimaryContainer = Color(0xFFCAE6FF),
    secondary = Color(0xFFBCC7DC),
    onSecondary = Color(0xFF263141),
    secondaryContainer = Color(0xFF3C4758),
    onSecondaryContainer = Color(0xFFD8E3F8),
    tertiary = Color(0xFFD8BFD8),
    onTertiary = Color(0xFF3E2844),
    tertiaryContainer = Color(0xFF553E5C),
    onTertiaryContainer = Color(0xFFF5DBF4),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF000000),  // Pure black for OLED
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF000000),     // Pure black for OLED
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF1A1C1E),
    onSurfaceVariant = Color(0xFFC2C7CE),
    outline = Color(0xFF8C9199),
    outlineVariant = Color(0xFF42474E),
)

@Composable
fun DuoGlassTheme(
    fontStyle: String = "POPPINS",
    themeMode: String = "DARK",
    content: @Composable () -> Unit
) {
    // Select font family
    val fontFamily = when (fontStyle) {
        "GEOMETRIC" -> OutfitFontFamily
        else -> PoppinsFontFamily
    }
    
    // Create typography with selected font
    val typography = createTypography(fontFamily)
    
    // Select color scheme
    val colorScheme = when (themeMode) {
        "LIGHT" -> LightColorScheme
        "OLED" -> OledColorScheme
        else -> DarkColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
