package com.smartplant.smartplantandroid.main.components.auth.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class AuthSession(
    @SerialName("is_active")
    val isActive: Boolean = true,

    @SerialName("session_uuid")
    val sessionUUID: String,

    @SerialName("user_id")
    val userId: Int,

    @SerialName("session_name")
    val sessionName: String,

    @Contextual
    @SerialName("created_at")
    val createdAt: LocalDateTime,

    @Contextual
    @SerialName("last_used")
    val lastUsed: LocalDateTime,

    @Contextual
    @SerialName("expires_at")
    val expiresAt: LocalDateTime
)
