package com.smartplant.smartplantandroid.main.components.auth.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    val id: Int,

    @SerialName("username")
    val username: String,

    @SerialName("created_at")
    val createdAt: LocalDateTime
)
