@file:Suppress("unused")

package com.smartplant.data_user.internal.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.smartplant.core_android.di.qualifiers.EncryptedSharedPrefs
import com.smartplant.data_user.internal.local.UserLocalDataSource
import com.smartplant.data_user.internal.local.UserLocalDataSourceImpl
import com.smartplant.data_user.internal.remote.UserRemoteDataSource
import com.smartplant.data_user.internal.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataUserModuleInternal {
    @Binds
    @Singleton
    internal abstract fun bindUserLocalDataSource(impl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    @Singleton
    internal abstract fun bindUserRemoteDataSource(impl: UserRemoteDataSourceImpl): UserRemoteDataSource

    companion object {
        @Provides
        @Singleton
        @EncryptedSharedPrefs("user")
        internal fun provideUserEncryptedSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
            return EncryptedSharedPreferences.create(
                "user",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}
