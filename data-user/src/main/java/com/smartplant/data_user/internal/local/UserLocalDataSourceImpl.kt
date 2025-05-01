@file:Suppress("unused")

package com.smartplant.data_user.internal.local

import android.content.SharedPreferences
import com.smartplant.core_android.data.BasePrefsDataSource
import com.smartplant.core_android.di.qualifiers.EncryptedSharedPrefs
import com.smartplant.domain_user.entities.UserPrivate
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefs("user") prefs: SharedPreferences,
) : BasePrefsDataSource(prefs), UserLocalDataSource {
    companion object {
        // Shared preferences keys
        private const val KEY_USER = "user"
    }

    override val user get(): UserPrivate? = getObject(KEY_USER)

    override fun saveUser(user: UserPrivate) = saveObject(KEY_USER, user)
}
