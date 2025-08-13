package com.iftikar.mediuser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.iftikar.mediuser.presentation.screens.login_screen.LoginScreen
import com.iftikar.mediuser.presentation.screens.waiting_screen.WaitingScreen

@Composable
fun NavApp() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.LoginScreen) {
        composable<Routes.LoginScreen> {
            LoginScreen(navHostController = navHostController)
        }

        composable<Routes.WaitingScreen> {
            val userId = it.toRoute<Routes.WaitingScreen>().userId
            WaitingScreen(userId = userId)
        }
    }
}