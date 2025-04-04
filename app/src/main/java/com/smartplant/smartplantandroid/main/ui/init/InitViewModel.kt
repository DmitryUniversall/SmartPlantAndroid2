package com.smartplant.smartplantandroid.main.ui.init

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartplant.smartplantandroid.R
import com.smartplant.smartplantandroid.core.logs.AppLogger
import com.smartplant.smartplantandroid.core.logs.AppLogger.logAsError
import com.smartplant.smartplantandroid.main.components.auth.services.auth.AuthService
import com.smartplant.smartplantandroid.main.components.auth.services.auth.AuthServiceImplST
import com.smartplant.smartplantandroid.main.components.auth.services.user.UserService
import com.smartplant.smartplantandroid.main.components.auth.services.user.UserServiceImplST
import com.smartplant.smartplantandroid.main.components.utils.services.utils.UtilsService
import com.smartplant.smartplantandroid.main.components.utils.services.utils.UtilsServiceImplST
import com.smartplant.smartplantandroid.main.network.exceptions.ApiRespondedErrorException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InitViewModel : ViewModel() {
    sealed interface UIState {
        data object Idle: UIState
        data object Loading : UIState
        data object ServerUnavailable : UIState
        data object UserUpdateSuccess : UIState
        data class NotAuthenticated(val isNewUser: Boolean, val statusCode: Int = -1) : UIState
    }

    // Aliases
    val utilsService: UtilsService get() = UtilsServiceImplST.instance
    val userService: UserService get() = UserServiceImplST.instance
    val authService: AuthService get() = AuthServiceImplST.instance

    private val _state = MutableStateFlow<UIState>(UIState.Idle)
    val state: StateFlow<UIState> = _state.asStateFlow()

    val isAuthenticated: Boolean get() = authService.isAuthenticated
    val isNewUser: Boolean get() = authService.isNewUser

    fun startInitialization() {
        if (_state.value == UIState.Loading) return

        viewModelScope.launch {
            _state.emit(UIState.Loading)

            val pingResult = utilsService.ping()
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
                userService.getMe()
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
