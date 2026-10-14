package com.example.finalappmoreforensicsandfiction.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ModernNoirColorScheme = darkColorScheme(
    primary = CopperAccent,
    onPrimary = MidnightBackground,
    primaryContainer = RaisedSurface,
    onPrimaryContainer = WarmIvoryText,
    secondary = BurgundyAccent,
    onSecondary = WarmIvoryText,
    secondaryContainer = DeepNavySurface,
    onSecondaryContainer = WarmIvoryText,
    tertiary = AmberHighlight,
    onTertiary = MidnightBackground,
    background = MidnightBackground,
    onBackground = WarmIvoryText,
    surface = DeepNavySurface,
    onSurface = WarmIvoryText,
    surfaceVariant = RaisedSurface,
    onSurfaceVariant = MutedText,
    outline = CopperAccent
)

@Composable
fun CasebookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = ModernNoirColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MidnightBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
