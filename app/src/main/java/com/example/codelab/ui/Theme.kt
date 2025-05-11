package com.example.codelab.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

// Define brown-white color palettes
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8D6E63),    // Brown 400
    onPrimary = Color.White,
    secondary = Color(0xFFD7CCC8),  // Light brown
    onSecondary = Color(0xFF3E2723),// Dark Brown
    background = Color(0xFF3E2723), // Dark Brown background
    onBackground = Color.White,
    surface = Color(0xFF5D4037),     // Brown 600
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF795548),     // Brown 500
    onPrimary = Color.White,
    secondary = Color(0xFFFFF8E1),   // Light cream
    onSecondary = Color(0xFF3E2723), // Dark Brown text
    background = Color(0xFFFFFDF9),  // Creamy white
    onBackground = Color(0xFF3E2723),
    surface = Color.White,
    onSurface = Color(0xFF3E2723)
)

@Composable
fun CodeLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Define consistent rounded shapes
    val shapes = Shapes(
        small = RoundedCornerShape(16.dp),
        medium = RoundedCornerShape(24.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = shapes
    ) {
        // Apply background behind status bar and content
        Box(
            Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            content()
        }
    }
}
