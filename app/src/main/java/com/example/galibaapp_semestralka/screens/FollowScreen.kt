package com.example.galibaapp_semestralka.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FollowScreen() {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box (contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text(text = "Follow Screen", fontSize = MaterialTheme.typography.displayLarge.fontSize, color = MaterialTheme.colorScheme.onSurface)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FollowScreenPreview() {
    FollowScreen()
}