package com.smartplant.feature_auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    sealed class AuthPageState() {
        object Login : AuthPageState()
        object Register : AuthPageState()
    }

    sealed class AuthEvent {
        object Success : AuthEvent()
        data class Error(val message: String) : AuthEvent()
    }

    data class AuthFormData(
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = "",
    )

    data class AuthFormErrors(
        val username: String? = null,
        val password: String? = null,
        val confirmPassword: String? = null,
    )

    // Page state
    private val _pageState = MutableStateFlow<AuthPageState>(AuthPageState.Login)
    val pageState: StateFlow<AuthPageState> get() = _pageState.asStateFlow()

    // Field values
    private val _form = MutableStateFlow(AuthFormData())
    val form: StateFlow<AuthFormData> = _form.asStateFlow()

    // Error messages
    private val _errors = MutableStateFlow(AuthFormErrors())
    val errors: StateFlow<AuthFormErrors> = _errors.asStateFlow()

    // Submit
    private val _submitState = MutableSharedFlow<AuthEvent>()
    val submitState: SharedFlow<AuthEvent> = _submitState.asSharedFlow()

    fun setPageState(state: AuthPageState) {
        if (_pageState.value == state) return
        _pageState.value = state
    }

    fun onUsernameChanged(username: String) = _form.update { it.copy(username = username) }
    fun onPasswordChanged(password: String) = _form.update { it.copy(password = password) }
    fun onConfirmChanged(confirmPassword: String) = _form.update { it.copy(confirmPassword = confirmPassword) }

    private fun validate(formData: AuthFormData): Boolean {
        var valid = true

        _errors.value = AuthFormErrors(
            username  = if (formData.username.isBlank()) "Required" else null,
            password  = if (formData.password.length < 6) { valid = false; "Too short" } else null,
            confirmPassword = if (formData.confirmPassword != formData.password) { valid = false; "Does not match" } else null
        )

        return valid
    }

    fun submit() = viewModelScope.launch {
        val formData = _form.value
        if (!validate(formData)) return@launch

        when (_pageState.value) {
            is AuthPageState.Login -> login(formData.username, formData.password)
            is AuthPageState.Register -> register(formData.username, formData.password)
        }
    }

    private suspend fun login(username: String, password: String) {

    }

    private suspend fun register(username: String, password: String) {

    }
}
