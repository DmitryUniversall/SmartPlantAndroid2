package com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDTO (
    @SerialName("refresh_token")
    val refreshToken: String
)
