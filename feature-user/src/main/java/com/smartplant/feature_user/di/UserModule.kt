@file:Suppress("unused")

package com.smartplant.feature_user.di

import com.smartplant.domain_user.service.UserService
import com.smartplant.domain_user.service.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    @Singleton
    abstract fun bindUserService(iml: UserServiceImpl): UserService
}
