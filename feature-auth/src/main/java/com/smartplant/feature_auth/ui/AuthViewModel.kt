package com.smartplant.feature_auth.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartplant.core_android.network.exceptions.ApiRespondedErrorException
import com.smartplant.core_android.utils.logs.AppLogger
import com.smartplant.core_android.utils.logs.AppLogger.logAsError
import com.smartplant.domain_auth.entities.AuthCredentials
import com.smartplant.domain_auth.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.smartplant.feature_auth.R

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {
    sealed class AuthPageState() {
        object Login : AuthPageState()
        object Register : AuthPageState()
    }

    sealed class AuthEvent {
        object Loading: AuthEvent()
        object Success : AuthEvent()
        data class Error(val applicationStatusCode: Int, val exception: Exception? = null) : AuthEvent()
    }

    data class AuthFormData(
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = "",
    )

    data class AuthFormErrors(
        @StringRes val username: Int? = null,
        @StringRes val password: Int? = null,
        @StringRes val confirmPassword: Int? = null
    )

    // Page state
    private val _pageState = MutableStateFlow<AuthPageState>(AuthPageState.Login)
    val pageState: StateFlow<AuthPageState> get() = _pageState.asStateFlow()

    // Error messages
    private val _formErrors = MutableStateFlow(AuthFormErrors())
    val formErrors: StateFlow<AuthFormErrors> = _formErrors.asStateFlow()

    // Submit
    private val _authState = MutableSharedFlow<AuthEvent>()
    val authState: SharedFlow<AuthEvent> = _authState.asSharedFlow()

    // Field values (private)
    private val _form = MutableStateFlow(AuthFormData())

    fun setPageState(state: AuthPageState) {
        if (_pageState.value == state) return
        _pageState.value = state
    }

    fun onUsernameChanged(username: String) = _form.update { it.copy(username = username) }
    fun onPasswordChanged(password: String) = _form.update { it.copy(password = password) }
    fun onPasswordConfirmChanged(confirmPassword: String) = _form.update { it.copy(confirmPassword = confirmPassword) }

    private fun validate(formData: AuthFormData): Boolean {
        var valid = true

        _formErrors.value = AuthFormErrors(
            username = if (formData.username.isBlank()) {
                valid = false; R.string.errorFieldRequired
            } else null,
            password = if (formData.password.length < 6) {
                valid = false; R.string.passwordTooShort
            } else null,
            confirmPassword = if (formData.confirmPassword != formData.password) {
                valid = pageState != AuthPageState.Register; R.string.passwordsNoMatch  // TODO: Refactor
            } else null
        )

        return valid
    }

    fun submit() = viewModelScope.launch {
        val formData = _form.value
        if (!validate(formData)) return@launch

        _authState.emit(AuthEvent.Loading)
        when (_pageState.value) {
            is AuthPageState.Login -> login(formData.username, formData.password)
            is AuthPageState.Register -> register(formData.username, formData.password)
        }
    }

    private suspend fun login(username: String, password: String) {
        try {
            authService.login(AuthCredentials(username, password), "android").getOrThrow()
            _authState.emit(AuthEvent.Success)
        } catch (error: ApiRespondedErrorException) {
            _authState.emit(AuthEvent.Error(error.result.apiResponse.applicationStatusCode, error))
        } catch (error: Exception) {
            error.logAsError()
            error.printStackTrace()
            _authState.emit(AuthEvent.Error(-1, error))
        }
    }

    private suspend fun register(username: String, password: String) {
        try {
            authService.register(AuthCredentials(username, password), "android").getOrThrow()
            _authState.emit(AuthEvent.Success)
        } catch (error: ApiRespondedErrorException) {
            _authState.emit(AuthEvent.Error(error.result.apiResponse.applicationStatusCode, error))
        } catch (error: Exception) {
            error.logAsError()
            error.printStackTrace()
            _authState.emit(AuthEvent.Error(-1, error))
        }
    }
}
