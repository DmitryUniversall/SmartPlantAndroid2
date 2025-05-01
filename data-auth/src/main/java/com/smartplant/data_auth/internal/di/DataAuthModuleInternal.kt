@file:Suppress("unused")

package com.smartplant.data_auth.internal.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.smartplant.core_android.di.qualifiers.EncryptedSharedPrefs
import com.smartplant.data_auth.internal.local.AuthLocalDataSource
import com.smartplant.data_auth.internal.local.AuthLocalDataSourceImpl
import com.smartplant.data_auth.internal.remote.AuthRemoteDataSource
import com.smartplant.data_auth.internal.remote.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataAuthModuleInternal {
    @Binds
    @Singleton
    internal abstract fun bindAuthLocalDataSource(impl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    @Singleton
    internal abstract fun bindAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    companion object {
        @Provides
        @Singleton
        @EncryptedSharedPrefs("auth")
        internal fun provideAuthEncryptedSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
            return EncryptedSharedPreferences.create(  // TODO: Args to config
                "auth",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}
