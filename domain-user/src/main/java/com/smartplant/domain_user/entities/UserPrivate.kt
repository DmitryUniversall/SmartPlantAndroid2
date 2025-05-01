package com.smartplant.domain_user.entities

import java.time.LocalDateTime

data class UserPrivate(
    val id: Int,
    val username: String,
    val createdAt: LocalDateTime,
)
