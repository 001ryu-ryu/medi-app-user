package com.iftikar.mediuser.presentation.screens.waiting_screen

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WaitingScreen(userId: String) {
    Text("Please wait here: $userId",
        modifier = Modifier.systemBarsPadding())
}