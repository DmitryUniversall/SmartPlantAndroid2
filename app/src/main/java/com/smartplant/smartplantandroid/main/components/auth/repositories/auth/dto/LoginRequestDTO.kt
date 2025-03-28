package com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(
    @SerialName("username")
    val username: String,

    @SerialName("session_name")
    val sessionName: String,

    @SerialName("password")
    val password: String
)
