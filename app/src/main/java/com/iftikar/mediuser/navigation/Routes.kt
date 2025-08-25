package com.iftikar.mediuser.navigation

import com.iftikar.mediuser.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object LoginScreen : Routes()
    @Serializable
    data class WaitingScreen(val userId: String) : Routes()
    @Serializable
    data class HomeScreen(val user: User) : Routes()

    @Serializable
    data object SplashScreen : Routes()
}