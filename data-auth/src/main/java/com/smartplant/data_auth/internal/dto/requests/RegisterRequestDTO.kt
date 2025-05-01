package com.smartplant.data_auth.internal.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequestDTO(
    @SerialName("username")
    val username: String,

    @SerialName("session_name")
    val sessionName: String,

    @SerialName("password")
    val password: String,
)
