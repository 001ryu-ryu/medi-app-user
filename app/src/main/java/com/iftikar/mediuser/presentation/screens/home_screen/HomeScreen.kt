package com.iftikar.mediuser.presentation.screens.home_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iftikar.mediuser.domain.model.User

@Composable
fun HomeScreen(user: User) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Text(
            text = "Hello ${user.name}",
            modifier = Modifier.padding(innerPadding)
        )
    }
}