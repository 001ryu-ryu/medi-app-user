package com.iftikar.mediuser.presentation.screens.login_screen

data class LoginScreenState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
