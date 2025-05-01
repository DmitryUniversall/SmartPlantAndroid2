@file:Suppress("unused")

package com.smartplant.data_auth.pub.di

import com.smartplant.data_auth.pub.repository.AuthRepositoryImpl
import com.smartplant.domain_auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataAuthModulePublic {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
