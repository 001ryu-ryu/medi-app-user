package com.iftikar.mediuser.presentation.screens.splash_screen

import android.util.Log
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iftikar.mediuser.navigation.Routes
import com.iftikar.mediuser.shared.PrefViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    prefViewModel: PrefViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val user = prefViewModel.userId.collectAsState()
    var text by remember { mutableStateOf("") }

    LaunchedEffect(user.value) {
        Log.d("Preff", "SplashScreen: ${user.value}")
        delay(2_000L)
        Log.d("Preff", "SplashScreen: ${user.value}")
        if (user.value != null) {
            navHostController.navigate(Routes.WaitingScreen(user.value!!)) {
                popUpTo<Routes.SplashScreen> {inclusive = true}
            }
        } else {
            navHostController.navigate(Routes.LoginScreen) {
                popUpTo<Routes.SplashScreen> {inclusive = true}
            }
        }
    }
    Text(
        text = text,
        modifier = Modifier.systemBarsPadding()
    )
}