package com.smartplant.data_auth.internal.local

import android.content.SharedPreferences
import com.smartplant.core_android.data.BasePrefsDataSource
import com.smartplant.core_android.di.qualifiers.EncryptedSharedPrefs
import com.smartplant.domain_auth.entities.AuthSession
import javax.inject.Inject

internal class AuthLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefs("auth") prefs: SharedPreferences,
) : BasePrefsDataSource(prefs), AuthLocalDataSource {
    companion object {
        // Shared preferences keys
        private const val KEY_SESSION = "session"
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
        private const val KEY_IS_NEW_USER = "isNewUser"
    }

    override val isAuthenticated: Boolean get() = accessToken != null && refreshToken != null
    override val isNewUser: Boolean get() = getBoolean(KEY_IS_NEW_USER, default = true)
    override val accessToken: String? get() = getString(KEY_ACCESS_TOKEN)
    override val refreshToken: String? get() = getString(KEY_REFRESH_TOKEN)
    override val session: AuthSession? get() = getObject(KEY_SESSION)

    override fun setNewUser(isNewUser: Boolean) = saveBoolean(KEY_IS_NEW_USER, isNewUser)
    override fun saveAccessToken(token: String) = saveString(KEY_ACCESS_TOKEN, token)
    override fun saveRefreshToken(token: String) = saveString(KEY_REFRESH_TOKEN, token)
    override fun saveSession(session: AuthSession) = saveObject(KEY_SESSION, session)
}
