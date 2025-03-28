package com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto

import com.smartplant.smartplantandroid.main.components.auth.entities.AuthSession
import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import kotlinx.serialization.SerialName


data class LoginResponseDTO(
    @SerialName("user")
    val user: UserPrivate,

    @SerialName("session")
    val session: AuthSession,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String
)
