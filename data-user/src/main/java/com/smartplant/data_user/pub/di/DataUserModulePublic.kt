@file:Suppress("unused")

package com.smartplant.data_user.pub.di

import com.smartplant.data_user.pub.repository.UserRepositoryImpl
import com.smartplant.domain_user.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataUserModulePublic {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
