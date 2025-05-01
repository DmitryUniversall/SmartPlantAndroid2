package com.smartplant.domain_user.repository

import com.smartplant.domain_user.entities.UserPrivate

interface UserRepository {
    val user: UserPrivate?

    fun saveUser(user: UserPrivate)

    suspend fun fetchMe(): Result<UserPrivate>
}
