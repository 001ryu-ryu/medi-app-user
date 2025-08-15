package com.iftikar.mediuser.presentation.screens.login_screen

sealed class LoginEvent {
    data object Idle : LoginEvent()
    data class EnterEmail(val email: String) : LoginEvent()
    data class EnterPassword(val password: String) : LoginEvent()
    data class Navigate(val userId: String) : LoginEvent()
    data object Login : LoginEvent()
}