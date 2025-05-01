package com.smartplant.domain_auth.service

import com.smartplant.core_common.exceptions.UnauthorizedException
import com.smartplant.domain_auth.entities.AuthCredentials
import com.smartplant.domain_auth.entities.AuthResult
import com.smartplant.domain_auth.entities.AuthSession
import com.smartplant.domain_auth.entities.AuthTokenPair
import com.smartplant.domain_auth.repository.AuthRepository
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : AuthService {
    override val isNewUser: Boolean get() = authRepository.isNewUser
    override val isAuthenticated: Boolean get() = authRepository.isAuthenticated
    override val accessToken: String? get() = authRepository.accessToken
    override val refreshToken: String? get() = authRepository.refreshToken
    override val session: AuthSession? get() = authRepository.session

    override suspend fun login(credentials: AuthCredentials, sessionName: String): Result<AuthResult> {
        return authRepository.login(credentials, sessionName)
    }

    override suspend fun register(credentials: AuthCredentials, sessionName: String): Result<AuthResult> {
        return authRepository.register(credentials, sessionName)
    }

    override suspend fun refresh(): Result<AuthTokenPair> {
        if (refreshToken == null) throw UnauthorizedException("Failed to refresh tokens: refresh token not found")
        return authRepository.refresh()
    }

    override suspend fun logout(): Result<Unit> {
        return authRepository.logout()
    }
}
