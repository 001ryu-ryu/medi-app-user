package com.iftikar.mediuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.iftikar.mediuser.navigation.NavApp
import com.iftikar.mediuser.shared.PrefViewModel
import com.iftikar.mediuser.ui.theme.MediUserTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefViewModel: PrefViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                prefViewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isLoading by prefViewModel.isLoading.collectAsState()
            val screen by prefViewModel.startDestination.collectAsState()
            MediUserTheme(dynamicColor = false) {
                if (!isLoading) {
                    NavApp(startDestination = screen)
                }
            }
        }
    }
}
