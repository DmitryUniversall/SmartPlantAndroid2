package com.smartplant.smartplantandroid.main.ui.activities.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    sealed class AuthPageState() {
        object Login : AuthPageState()
        object Register: AuthPageState()
    }

    private val _state = MutableStateFlow<AuthPageState>(AuthPageState.Login)
    val state: StateFlow<AuthPageState> get() = _state.asStateFlow()

    fun setState(state: AuthPageState) {
        if (_state.value == state) return
        _state.value = state
    }
}
