package com.iftikar.mediuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.iftikar.mediuser.navigation.NavApp
import com.iftikar.mediuser.presentation.screens.waiting_screen.WaitingScreen
import com.iftikar.mediuser.ui.theme.MediUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MediUserTheme(dynamicColor = false) {
                val navController = rememberNavController()
//                NavApp()
                WaitingScreen(
                    userId = "e0b087e6-350f-4def-bf8a-e1ea1f7863b9",
                    navHostController = navController
                )
            }
        }
    }
}
