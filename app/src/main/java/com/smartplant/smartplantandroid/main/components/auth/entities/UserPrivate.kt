package com.smartplant.smartplantandroid.main.components.auth.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserPrivate(
    @SerialName("id")
    val id: Int,

    @SerialName("username")
    val username: String,

    @Contextual
    @SerialName("created_at")
    val createdAt: LocalDateTime
)
