package com.smartplant.feature_init.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartplant.api_utils.pub.utils.ApiUtils
import com.smartplant.core_android.network.exceptions.ApiRespondedErrorException
import com.smartplant.core_android.utils.logs.AndroidAppLogger.logAsError
import com.smartplant.domain_auth.service.AuthService
import com.smartplant.domain_user.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InitViewModel @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    private val apiUtils: ApiUtils,
) : ViewModel() {
    sealed interface UIState {
        data object Idle : UIState
        data object Loading : UIState
        data object ServerUnavailable : UIState
        data object UserUpdateSuccess : UIState
        data class NotAuthenticated(val isNewUser: Boolean, val statusCode: Int = -1) : UIState
    }

    // Aliases
    private val _state = MutableStateFlow<UIState>(UIState.Idle)
    val state: StateFlow<UIState> = _state.asStateFlow()

    val isAuthenticated: Boolean get() = authService.isAuthenticated
    val isNewUser: Boolean get() = authService.isNewUser

    fun startInitialization() {
        if (_state.value == UIState.Loading) return

        viewModelScope.launch {
            _state.emit(UIState.Loading)

            val pingResult = apiUtils.ping()
            if (pingResult.isFailure) {
                _state.emit(UIState.ServerUnavailable)
                return@launch
            }

            if (isNewUser) {
                _state.emit(UIState.NotAuthenticated(isNewUser = true))
                return@launch
            }

            if (!isAuthenticated) {
                _state.emit(UIState.NotAuthenticated(isNewUser = false))
                return@launch
            }

            try {
                userService.fetchMe()
            } catch (error: ApiRespondedErrorException) {
                _state.emit(UIState.NotAuthenticated(false, error.result.apiResponse.applicationStatusCode))
                return@launch
            } catch (error: Exception) {
                error.logAsError()
                authService.logout().onFailure { error -> error.logAsError() }  // We don't really care if logout fails
                _state.emit(UIState.NotAuthenticated(false))
            }

            _state.emit(UIState.UserUpdateSuccess)
        }
    }
}
