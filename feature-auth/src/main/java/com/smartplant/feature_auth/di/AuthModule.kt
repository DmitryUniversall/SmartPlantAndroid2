@file:Suppress("unused")

package com.smartplant.feature_auth.di

import com.smartplant.domain_auth.service.AuthService
import com.smartplant.domain_auth.service.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindAuthService(iml: AuthServiceImpl): AuthService
}
