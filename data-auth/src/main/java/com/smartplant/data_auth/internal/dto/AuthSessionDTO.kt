package com.smartplant.data_auth.internal.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthSessionDTO(
    @SerialName("is_active")
    val isActive: Boolean = true,

    @SerialName("session_uuid")
    val sessionUUID: String,

    @SerialName("user_id")
    val userId: Int,

    @SerialName("session_name")
    val sessionName: String,

    @SerialName("created_at")
    val createdAt: LocalDateTime,

    @SerialName("last_used")
    val lastUsed: LocalDateTime,

    @SerialName("expires_at")
    val expiresAt: LocalDateTime,
)
