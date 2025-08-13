package com.iftikar.mediuser.presentation.screens.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediuser.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            loginUseCase(email, password).collect { apiOperation ->
                apiOperation.onSuccess {loginResponse ->
                    if (loginResponse.status == 200) {
                        _loginState.value = LoginState.Success(
                            loginResponse = loginResponse
                        )
                    } else {
                        _loginState.value = LoginState.Error(
                            message = loginResponse.message
                        )
                    }
                }.onFailure { exception ->
                    _loginState.value = LoginState.Error(
                        message = exception.message ?: "Unknown Error!"
                    )
                }
            }
        }
    }
}

















