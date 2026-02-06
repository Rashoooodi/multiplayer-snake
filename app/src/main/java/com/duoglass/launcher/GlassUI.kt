package com.duoglass.launcher

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * GlassCard - Reusable Glassmorphism UI Component
 * Implements Windows 11 Acrylic x Material 3 design language
 * 
 * Features:
 * - 24dp corner radius (strictly enforced)
 * - Blur effect on Android 12+ (30.dp)
 * - High-alpha scrim fallback for older APIs
 * - Border with subtle gradient
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val cornerRadius = 24.dp
    val shape = RoundedCornerShape(cornerRadius)
    
    // Determine if we can use blur effect
    val canUseBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    
    // Background color with alpha
    val backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
    
    // Border color with subtle gradient effect
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    
    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (canUseBlur) {
                    Modifier
                        .background(backgroundColor)
                        .blur(30.dp)
                } else {
                    // Fallback for older APIs - use higher alpha scrim
                    Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                backgroundColor.copy(alpha = 0.95f),
                                backgroundColor.copy(alpha = 0.85f)
                            )
                        )
                    )
                }
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
    ) {
        content()
    }
}

/**
 * GlassButton - Glass-styled clickable button
 */
@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val cornerRadius = 24.dp
    val shape = RoundedCornerShape(cornerRadius)
    
    val canUseBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    
    val backgroundColor = if (enabled) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
    }
    
    val borderColor = if (enabled) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    }
    
    Row(
        modifier = modifier
            .clip(shape)
            .then(
                if (canUseBlur) {
                    Modifier
                        .background(backgroundColor)
                        .blur(20.dp)
                } else {
                    Modifier.background(backgroundColor)
                }
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = shape
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        content()
    }
}

/**
 * GlassSurface - Full-screen glassmorphic surface
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        content()
    }
}

/**
 * SelectableGlassCard - Glass card with selection state
 */
@Composable
fun SelectableGlassCard(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val cornerRadius = 24.dp
    val shape = RoundedCornerShape(cornerRadius)
    val canUseBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    }
    
    val borderColor = if (selected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    }
    
    val borderWidth = if (selected) 3.dp else 1.dp
    
    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (canUseBlur) {
                    Modifier
                        .background(backgroundColor)
                        .blur(25.dp)
                } else {
                    Modifier.background(backgroundColor)
                }
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            )
            .clickable { onClick() }
    ) {
        content()
    }
}
