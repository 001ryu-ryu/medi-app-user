package com.iftikar.mediuser.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediuser.util.PreferenceDataStore
import com.iftikar.mediuser.util.USER_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrefViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.asStateFlow()

    init {
        getUserPref()
    }

    private fun getUserPref() {
        viewModelScope.launch {
            val userId = preferenceDataStore.getUser(USER_ID_KEY)

            if (userId != null) {
                _userId.value = userId
            } else {
                _userId.value = null
            }
        }
    }
}











