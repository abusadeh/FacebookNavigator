package com.example.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class ShortcutItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val url: String
)
