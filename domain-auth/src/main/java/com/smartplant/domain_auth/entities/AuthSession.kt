package com.smartplant.domain_auth.entities

import kotlinx.datetime.LocalDateTime

data class AuthSession(
    val isActive: Boolean,
    val sessionUUID: String,
    val userId: Int,
    val sessionName: String,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime,
    val expiresAt: LocalDateTime,
)
