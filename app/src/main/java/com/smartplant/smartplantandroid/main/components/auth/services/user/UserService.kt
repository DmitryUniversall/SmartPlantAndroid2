package com.smartplant.smartplantandroid.main.components.auth.services.user

import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate

interface UserService {
    suspend fun getMe(): Result<UserPrivate>
}
