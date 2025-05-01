package com.smartplant.data_auth.internal.local

import com.smartplant.domain_auth.entities.AuthSession

internal interface AuthLocalDataSource {
    val isNewUser: Boolean
    val isAuthenticated: Boolean
    val accessToken: String?
    val refreshToken: String?
    val session: AuthSession?

    fun setNewUser(isNewUser: Boolean)
    fun saveAccessToken(token: String)
    fun saveRefreshToken(token: String)
    fun saveSession(session: AuthSession)

    fun clear()
}
