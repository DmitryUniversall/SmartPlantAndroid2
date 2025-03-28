package com.smartplant.smartplantandroid.main.components.auth.entities

import kotlinx.serialization.SerialName

data class AuthInfo(
    @SerialName("user")
    val user: UserPrivate,

    @SerialName("session")
    val session: AuthSession
)
