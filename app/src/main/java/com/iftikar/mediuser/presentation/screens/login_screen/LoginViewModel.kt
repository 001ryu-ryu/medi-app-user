package com.iftikar.mediuser.presentation.screens.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediuser.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginScreenState>(LoginScreenState())
    val loginState: StateFlow<LoginScreenState> = _loginState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
        initialValue = LoginScreenState()
    )

    private val _eventState: Channel<LoginEvent> = Channel()
    val eventState = _eventState.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnterEmail -> {
                _loginState.update {
                    it.copy(email = event.email)
                }
            }

            is LoginEvent.EnterPassword -> {
                _loginState.update {
                    it.copy(password = event.password)
                }
            }

            is LoginEvent.Login -> {
                login()
            }

            else -> {}
        }
    }


    fun login() {
        viewModelScope.launch {
            _loginState.update {
                it.copy(isLoading = true, error = null)
            }
            loginUseCase(
                _loginState.value.email,
                _loginState.value.password
            ).collect { apiOperation ->

                apiOperation.onSuccess { loginResponse ->
                    _loginState.update {
                        it.copy(isLoading = false)
                    }
                    if (loginResponse.status == 200) {
                        viewModelScope.launch(Dispatchers.IO) {
                            _eventState.send(
                                LoginEvent.Navigate(userId = loginResponse.message)
                            )
                        }

                    } else {
                        _loginState.update {
                            it.copy(error = loginResponse.message)
                        }
                    }
                }.onFailure { exception ->
                    _loginState.update {
                        it.copy(error = exception.message ?: "Some error occurred!",
                            isLoading = false)
                    }
                }
            }
        }
    }
}

















