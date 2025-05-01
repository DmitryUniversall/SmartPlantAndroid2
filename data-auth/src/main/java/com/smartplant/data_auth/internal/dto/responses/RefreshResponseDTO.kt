package com.smartplant.data_auth.internal.dto.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponseDTO(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,
)
