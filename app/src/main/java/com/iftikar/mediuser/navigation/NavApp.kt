package com.iftikar.mediuser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.iftikar.mediuser.domain.model.User
import com.iftikar.mediuser.presentation.screens.home_screen.HomeScreen
import com.iftikar.mediuser.presentation.screens.login_screen.LoginScreen
import com.iftikar.mediuser.presentation.screens.waiting_screen.WaitingScreen
import com.iftikar.mediuser.util.CustomNavType
import kotlin.reflect.typeOf

@Composable
fun NavApp() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.LoginScreen) {
        composable<Routes.LoginScreen> {
            LoginScreen(navHostController = navHostController)
        }

        composable<Routes.WaitingScreen> {
            val userId = it.toRoute<Routes.WaitingScreen>().userId
            WaitingScreen(userId = userId, navHostController = navHostController)
        }

        composable<Routes.HomeScreen>(
            typeMap = mapOf(
                typeOf<User>() to CustomNavType.UserType
            )
        ) {
            val user = it.toRoute<Routes.HomeScreen>().user
            HomeScreen(user = user)
        }
    }
}