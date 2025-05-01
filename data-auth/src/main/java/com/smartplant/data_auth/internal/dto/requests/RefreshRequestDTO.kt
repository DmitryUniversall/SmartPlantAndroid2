package com.smartplant.data_auth.internal.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshRequestDTO(
    @SerialName("refresh_token")
    val refreshToken: String,
)
