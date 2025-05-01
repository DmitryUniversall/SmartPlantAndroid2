package com.smartplant.domain_auth.entities

import com.smartplant.domain_user.entities.UserPrivate

data class AuthInfo(
    val user: UserPrivate,
    val session: AuthSession,
)
