package com.smartplant.smartplantandroid.main.components.auth.services

import android.content.Context
import com.smartplant.smartplantandroid.main.components.auth.internal_utils.AuthInfoManager
import kotlinx.coroutines.sync.Mutex

class AuthServiceST private constructor(context: Context): AuthService {
    companion object {
        @Volatile
        private var _instance: AuthServiceST? = null
        val instance: AuthServiceST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance(context: Context) = _instance ?: synchronized(this) { _instance ?: AuthServiceST(context).also { _instance = it } }
    }

    private val authInfoManager = AuthInfoManager(context)
    private val refreshLock = Mutex()

    override fun isAuthenticated() = authInfoManager.isAuthenticated()
    override fun getAccessToken() = authInfoManager.getAccessToken()
    override fun getRefreshToken() = authInfoManager.getRefreshToken()

    override suspend fun refresh() = TODO()
    override suspend fun login() = TODO()
    override suspend fun register() = TODO()
    override suspend fun logout() = TODO()
    override suspend fun getMe() = TODO()
}
