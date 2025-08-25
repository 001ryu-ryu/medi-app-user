package com.iftikar.mediuser.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.iftikar.mediuser.domain.model.User
import com.iftikar.mediuser.presentation.screens.home_screen.HomeScreen
import com.iftikar.mediuser.presentation.screens.login_screen.LoginScreen
import com.iftikar.mediuser.presentation.screens.waiting_screen.WaitingScreen
import com.iftikar.mediuser.navigation.CustomNavType
import com.iftikar.mediuser.presentation.screens.splash_screen.SplashScreen
import com.iftikar.mediuser.shared.PrefViewModel
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Composable
fun NavApp(
    prefViewModel: PrefViewModel = hiltViewModel()
) {

    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Routes.SplashScreen) {
        composable<Routes.SplashScreen> {
            SplashScreen(navHostController = navHostController)
        }
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