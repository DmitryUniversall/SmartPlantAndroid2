package com.smartplant.data_user.pub.repository

import com.smartplant.data_user.internal.local.UserLocalDataSource
import com.smartplant.data_user.internal.mappers.toUserPrivate
import com.smartplant.data_user.internal.remote.UserRemoteDataSource
import com.smartplant.domain_user.entities.UserPrivate
import com.smartplant.domain_user.repository.UserRepository
import javax.inject.Inject
import kotlin.map
import kotlin.onSuccess

class UserRepositoryImpl @Inject internal constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override val user: UserPrivate? get() = localDataSource.user

    override fun saveUser(user: UserPrivate) {
        localDataSource.saveUser(user)
    }

    override suspend fun fetchMe(): Result<UserPrivate> {
        return remoteDataSource.getMe().map { it.toUserPrivate() }.onSuccess { user ->
            localDataSource.saveUser(user)
        }
    }
}
