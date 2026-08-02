package com.allsoft.erp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ERPColorScheme = darkColorScheme(
    primary       = Blue500,
    onPrimary     = White,
    primaryContainer   = Navy700,
    secondary     = Gold500,
    onSecondary   = Navy900,
    background    = Navy900,
    onBackground  = White,
    surface       = Navy800,
    onSurface     = White,
    surfaceVariant     = Navy700,
    onSurfaceVariant   = Gray400,
    error         = Error,
    onError       = White,
    outline       = Navy700,
    outlineVariant     = Color(0xFF1E2F50)
)

@Composable
fun ERPMobileTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ERPColorScheme,
        typography  = ERPTypography,
        content     = content
    )
}
