package com.iftikar.mediuser.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object LoginScreen : Routes()
    @Serializable
    data class WaitingScreen(val userId: String) : Routes()
}