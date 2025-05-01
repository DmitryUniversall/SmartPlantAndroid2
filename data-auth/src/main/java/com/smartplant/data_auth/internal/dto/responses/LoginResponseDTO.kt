package com.smartplant.data_auth.internal.dto.responses

import com.smartplant.core_dto.UserPrivateDTO
import com.smartplant.data_auth.internal.dto.AuthSessionDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponseDTO(
    @SerialName("user")
    val user: UserPrivateDTO,

    @SerialName("session")
    val session: AuthSessionDTO,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,
)
