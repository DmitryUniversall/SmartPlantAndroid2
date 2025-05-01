package com.smartplant.data_user.internal.local

import com.smartplant.domain_user.entities.UserPrivate

internal interface UserLocalDataSource {
    val user: UserPrivate?

    fun saveUser(user: UserPrivate)

    fun clear()
}
