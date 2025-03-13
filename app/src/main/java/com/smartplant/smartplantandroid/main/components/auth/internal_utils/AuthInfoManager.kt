package com.smartplant.smartplantandroid.main.components.auth.internal_utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.smartplant.smartplantandroid.main.components.auth.entities.AuthSession
import com.smartplant.smartplantandroid.main.components.auth.entities.User
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import java.security.GeneralSecurityException

class AuthInfoManager(
    context: Context
) {
    companion object {
        private var PREFERENCES_FILENAME = "auth"

        // Shared preferences keys
        private const val KEY_USER = "user"
        private const val KEY_SESSION = "session"
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
    }

    private val preferences = createEncryptedPreferences(context)

    // Cache
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var user: User? = null
    private var session: AuthSession? = null

    private fun createEncryptedPreferences(ctx: Context): SharedPreferences {
        return try {
            EncryptedSharedPreferences.create(
                PREFERENCES_FILENAME,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                ctx,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun resetCache() {
        accessToken = null
        refreshToken = null
        user = null
        session = null
    }

    fun clear() {
        preferences.edit().clear().apply()
        resetCache()
    }

    fun isAuthenticated() = accessToken != null && refreshToken != null

    fun setAccessToken(token: String) = preferences.edit().putString(KEY_ACCESS_TOKEN, token).apply().also { accessToken = token }
    fun getAccessToken(): String? = accessToken ?: preferences.getString(KEY_ACCESS_TOKEN, null).also { accessToken = it }

    fun setRefreshToken(token: String) = preferences.edit().putString(KEY_REFRESH_TOKEN, token).apply().also { refreshToken = token }
    fun getRefreshToken(): String? = refreshToken ?: preferences.getString(KEY_REFRESH_TOKEN, null).also { refreshToken = it }

    fun setUser(user: User) = preferences.edit().putString(KEY_USER, Json.encodeToString(user)).apply().also { this.user = user }
    fun getUser(): User? = user ?: preferences.getString(KEY_USER, null)?.let { Json.decodeFromString<User>(it) }.also { user = it }

    fun setSession(session: AuthSession) = preferences.edit().putString(KEY_SESSION, Json.encodeToString(session)).apply().also { this.session = session }
    fun getSession(): AuthSession? = session ?: preferences.getString(KEY_SESSION, null)?.let { Json.decodeFromString<AuthSession>(it) }.also { session = it }
}
