package com.smartplant.smartplantandroid.main.components.auth.services

interface AuthService {
    fun isAuthenticated(): Boolean
    fun getAccessToken(): String?
    fun getRefreshToken(): String?

    suspend fun refresh()
    suspend fun login()
    suspend fun register()
    suspend fun logout()
    suspend fun getMe()
}
