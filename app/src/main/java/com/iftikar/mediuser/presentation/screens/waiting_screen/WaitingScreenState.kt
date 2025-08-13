package com.iftikar.mediuser.presentation.screens.waiting_screen

import com.iftikar.mediuser.domain.model.User

sealed interface WaitingScreenState {
    object Loading : WaitingScreenState
    data class Success(val user: User) : WaitingScreenState
    data class Error(val error: String) : WaitingScreenState
}