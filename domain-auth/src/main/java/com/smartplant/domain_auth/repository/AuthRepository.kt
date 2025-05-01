package com.smartplant.domain_auth.repository

import com.smartplant.domain_auth.entities.AuthCredentials
import com.smartplant.domain_auth.entities.AuthResult
import com.smartplant.domain_auth.entities.AuthSession
import com.smartplant.domain_auth.entities.AuthTokenPair

interface AuthRepository {
    val isNewUser: Boolean
    val isAuthenticated: Boolean
    val accessToken: String?
    val refreshToken: String?
    val session: AuthSession?

    suspend fun login(credentials: AuthCredentials, sessionName: String): Result<AuthResult>
    suspend fun register(credentials: AuthCredentials, sessionName: String): Result<AuthResult>
    suspend fun refresh(): Result<AuthTokenPair>
    suspend fun logout(): Result<Unit>
}
