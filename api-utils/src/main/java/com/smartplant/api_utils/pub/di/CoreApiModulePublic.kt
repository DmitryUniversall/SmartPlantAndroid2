package com.smartplant.api_utils.pub.di

import com.smartplant.api_utils.pub.utils.ApiUtils
import com.smartplant.api_utils.pub.utils.ApiUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreApiModulePublic {
    @Binds
    @Singleton
    abstract fun bindApiUtils(impl: ApiUtilsImpl): ApiUtils
}
