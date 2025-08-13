package com.iftikar.mediuser.presentation.screens.waiting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediuser.domain.usecase.GetSpecificUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive
import javax.inject.Inject

@HiltViewModel
class WaitingScreenViewModel @Inject constructor(private val getSpecificUserUseCase: GetSpecificUserUseCase)
    : ViewModel() {
    private val _state = MutableStateFlow<WaitingScreenState>(WaitingScreenState.Loading)
    val state = _state.asStateFlow()

    private var pollingJob: Job? = null

    fun startPollingUser(userId: String, intervalMs: Long = 5_000L) {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                getSpecificUserUseCase(userId = userId).collect { apiOperation ->
                    apiOperation.onSuccess { user ->
                        _state.update {
                            return@update WaitingScreenState.Success(user = user)
                        }
                    }.onFailure { exception ->
                        _state.update {
                            return@update WaitingScreenState.Error(
                                error = exception.message ?: "Unknown Error!"
                            )
                        }
                    }
                }
                delay(intervalMs)
            }
        }
    }
}
