package com.smartplant.domain_auth.entities

import java.time.LocalDateTime

data class AuthSession(
    val isActive: Boolean,
    val sessionUUID: String,
    val userId: Int,
    val sessionName: String,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime,
    val expiresAt: LocalDateTime,
)
