package com.smartplant.smartplantandroid.main.components.auth.internal_utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.smartplant.smartplantandroid.main.components.auth.entities.AuthSession
import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import java.security.GeneralSecurityException

class AuthInfoManager(
    context: Context
) {
    companion object {
        private const val PREFERENCES_FILENAME = "auth"

        // Shared preferences keys
        private const val KEY_USER = "user"
        private const val KEY_SESSION = "session"
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
        private const val KEY_IS_NEW_USER = "isNewUser"
    }

    // Utils
    private val preferences = createEncryptedPreferences(context)

    // Cache
    private var cachedIsNewUser: Boolean? = null
    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null
    private var cachedSession: AuthSession? = null
    private var cachedUser: UserPrivate? = null

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
        cachedAccessToken = null
        cachedRefreshToken = null
        cachedUser = null
        cachedSession = null
    }

    fun clear() {
        preferences.edit().clear().apply()
        resetCache()
    }

    val isAuthenticated: Boolean get() = accessToken != null && refreshToken != null
    var isNewUser: Boolean
        get() = cachedIsNewUser ?: preferences.getBoolean(KEY_IS_NEW_USER, true).also { cachedIsNewUser = it }
        set(isNewUser) = preferences.edit().putBoolean(KEY_IS_NEW_USER, isNewUser).apply().also { cachedIsNewUser = isNewUser }

    var accessToken: String?
        get() = cachedAccessToken ?: preferences.getString(KEY_ACCESS_TOKEN, null).also { cachedAccessToken = it }
        set(token) = preferences.edit().putString(KEY_ACCESS_TOKEN, token).apply().also { cachedAccessToken = token }

    var refreshToken: String?
        get() = cachedRefreshToken ?: preferences.getString(KEY_REFRESH_TOKEN, null).also { cachedRefreshToken = it }
        set(token) = preferences.edit().putString(KEY_REFRESH_TOKEN, token).apply().also { cachedRefreshToken = token }

    var session: AuthSession?
        get()  = cachedSession ?: preferences.getString(KEY_SESSION, null)?.let { Json.decodeFromString<AuthSession>(it) }.also { cachedSession = it }
        set(session) = preferences.edit().putString(KEY_SESSION, Json.encodeToString(session)).apply().also { this.cachedSession = session }

    var user: UserPrivate?
        get() = cachedUser ?: preferences.getString(KEY_USER, null)?.let { Json.decodeFromString<UserPrivate>(it) }.also { cachedUser = it }
        set(user) = preferences.edit().putString(KEY_USER, Json.encodeToString(user)).apply().also { this.cachedUser = user }
}
