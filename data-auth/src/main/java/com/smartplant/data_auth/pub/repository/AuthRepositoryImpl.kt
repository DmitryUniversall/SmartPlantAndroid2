@file:Suppress("unused")

package com.smartplant.data_auth.pub.repository

import com.smartplant.core_common.async.finally
import com.smartplant.data_auth.internal.dto.requests.LoginRequestDTO
import com.smartplant.data_auth.internal.dto.requests.RefreshRequestDTO
import com.smartplant.data_auth.internal.dto.requests.RegisterRequestDTO
import com.smartplant.data_auth.internal.local.AuthLocalDataSource
import com.smartplant.data_auth.internal.mappers.toAuthResult
import com.smartplant.data_auth.internal.mappers.toAuthTokenPair
import com.smartplant.data_auth.internal.remote.AuthRemoteDataSource
import com.smartplant.domain_auth.entities.AuthCredentials
import com.smartplant.domain_auth.entities.AuthResult
import com.smartplant.domain_auth.entities.AuthSession
import com.smartplant.domain_auth.entities.AuthTokenPair
import com.smartplant.domain_auth.repository.AuthRepository
import com.smartplant.domain_user.repository.UserRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject internal constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
    private val userRepository: UserRepository,
) : AuthRepository {
    override val isNewUser: Boolean get() = localDataSource.isNewUser
    override val isAuthenticated: Boolean get() = localDataSource.isAuthenticated
    override val accessToken: String? get() = localDataSource.accessToken
    override val refreshToken: String? get() = localDataSource.refreshToken
    override val session: AuthSession? get() = localDataSource.session

    override suspend fun login(credentials: AuthCredentials, sessionName: String): Result<AuthResult> {
        return remoteDataSource.login(
            LoginRequestDTO(username = credentials.username, password = credentials.password, sessionName = sessionName)
        ).map { it.toAuthResult() }.onSuccess { response ->
            localDataSource.saveAccessToken(response.tokenPair.accessToken)
            localDataSource.saveRefreshToken(response.tokenPair.refreshToken)
            userRepository.saveUser(response.authInfo.user)
            localDataSource.saveSession(response.authInfo.session)
        }
    }

    override suspend fun register(credentials: AuthCredentials, sessionName: String): Result<AuthResult> {
        return remoteDataSource.register(
            RegisterRequestDTO(username = credentials.username, password = credentials.password, sessionName = sessionName)
        ).map { it.toAuthResult() }.onSuccess { response ->
            localDataSource.saveAccessToken(response.tokenPair.accessToken)
            localDataSource.saveRefreshToken(response.tokenPair.refreshToken)
            userRepository.saveUser(response.authInfo.user)
            localDataSource.saveSession(response.authInfo.session)
        }
    }

    override suspend fun refresh(): Result<AuthTokenPair> {
        return remoteDataSource.refresh(
            RefreshRequestDTO(refreshToken = localDataSource.refreshToken!!)
        ).map { it.toAuthTokenPair() }.onSuccess { response ->
            localDataSource.saveAccessToken(response.accessToken)
            localDataSource.saveRefreshToken(response.refreshToken)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return remoteDataSource.logout().finally {
            localDataSource.clear()
        }
    }
}
