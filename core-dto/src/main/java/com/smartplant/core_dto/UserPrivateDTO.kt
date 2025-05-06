package com.smartplant.core_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class UserPrivateDTO(
    @SerialName("id")
    val id: Int,

    @SerialName("username")
    val username: String,

    @SerialName("created_at")
    val createdAt: LocalDateTime,
)
