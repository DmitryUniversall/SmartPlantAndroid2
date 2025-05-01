package com.smartplant.domain_user.service

import com.smartplant.domain_user.entities.UserPrivate

interface UserService {
    val user: UserPrivate?

    suspend fun getOrFetchMe(): Result<UserPrivate>
    suspend fun fetchMe(): Result<UserPrivate>
}
