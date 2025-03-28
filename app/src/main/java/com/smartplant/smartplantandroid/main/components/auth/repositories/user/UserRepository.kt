package com.smartplant.smartplantandroid.main.components.auth.repositories.user

import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate

interface UserRepository {
    suspend fun getMe(): Result<UserPrivate>
}
