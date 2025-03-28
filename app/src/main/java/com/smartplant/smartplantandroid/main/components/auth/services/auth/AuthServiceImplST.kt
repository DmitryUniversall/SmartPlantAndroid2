package com.smartplant.smartplantandroid.main.components.auth.services.auth

import android.content.Context
import com.smartplant.smartplantandroid.main.components.auth.entities.AuthTokenPair
import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import com.smartplant.smartplantandroid.main.components.auth.internal_utils.AuthInfoManager
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.AuthRepository
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.AuthRepositoryImplST
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginResponseDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RefreshRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterResponseDTO
import com.smartplant.smartplantandroid.main.exceptions.UnauthorizedException

class AuthServiceImplST private constructor(context: Context) : AuthService {
    companion object {
        @Volatile
        private var _instance: AuthServiceImplST? = null
        val instance: AuthServiceImplST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance(context: Context) = _instance ?: synchronized(this) { _instance ?: AuthServiceImplST(context).also { _instance = it } }
    }

    private val authInfoManager = AuthInfoManager(context)
    private val authRepository: AuthRepository = AuthRepositoryImplST.instance

    override val isAuthenticated: Boolean
        get() = authInfoManager.isAuthenticated

    override var accessToken: String?
        get() = authInfoManager.accessToken
        set(token) {
            authInfoManager.accessToken = token
        }

    override var refreshToken: String?
        get() = authInfoManager.refreshToken
        set(token) {
            authInfoManager.refreshToken = token
        }

    override var user: UserPrivate?
        get() = authInfoManager.user
        set(user) {
            authInfoManager.user = user
        }

    override suspend fun login(username: String, sessionName: String, password: String): Result<LoginResponseDTO> {
        return authRepository.login(LoginRequestDTO(username, sessionName, password)).onSuccess { response ->
            authInfoManager.accessToken = response.accessToken
            authInfoManager.refreshToken = response.accessToken
            authInfoManager.user = response.user
            authInfoManager.session = response.session
        }
    }

    override suspend fun register(username: String, sessionName: String, password: String): Result<RegisterResponseDTO> {
        return authRepository.register(RegisterRequestDTO(username, sessionName, password)).onSuccess { response ->
            authInfoManager.accessToken = response.accessToken
            authInfoManager.refreshToken = response.accessToken
            authInfoManager.user = response.user
            authInfoManager.session = response.session
        }
    }

    override suspend fun refresh(): Result<AuthTokenPair> {
        if (refreshToken == null) throw UnauthorizedException("Failed to refresh tokens: refresh token not found")

        return authRepository.refresh(RefreshRequestDTO(refreshToken!!)).onSuccess { response ->
            authInfoManager.accessToken = response.accessToken
            authInfoManager.refreshToken = response.refreshToken
        }
    }

    override suspend fun logout(): Result<Unit> {
        return authRepository.logout().onSuccess {
            authInfoManager.clear()
        }
    }
}
