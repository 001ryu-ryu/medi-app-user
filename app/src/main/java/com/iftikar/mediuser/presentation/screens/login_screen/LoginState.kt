package com.iftikar.mediuser.presentation.screens.login_screen

import com.iftikar.mediuser.domain.model.LoginResponse

sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    data class Error(val message: String) : LoginState
    data class Success(val loginResponse: LoginResponse) : LoginState
}
