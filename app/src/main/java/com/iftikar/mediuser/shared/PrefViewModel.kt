package com.iftikar.mediuser.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediuser.navigation.Routes
import com.iftikar.mediuser.util.PreferenceDataStore
import com.iftikar.mediuser.util.USER_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrefViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination = MutableStateFlow<Routes>(Routes.LoginScreen)
    val startDestination = _startDestination.asStateFlow()

    init {
        getUserPref()
    }

    private fun getUserPref() {
        viewModelScope.launch {
            val userId = preferenceDataStore.getUser(USER_ID_KEY)

            if (userId != null) {
                _startDestination.value = Routes.WaitingScreen(userId)
            } else {
                _startDestination.value = Routes.LoginScreen
            }

            _isLoading.value = false
        }
    }
}











