package com.smartplant.data_auth.internal.mappers

import com.smartplant.data_auth.internal.dto.AuthSessionDTO
import com.smartplant.domain_auth.entities.AuthSession

internal fun AuthSessionDTO.toEntity(): AuthSession {
    return AuthSession(
        isActive = isActive,
        sessionUUID = sessionUUID,
        userId = userId,
        sessionName = sessionName,
        createdAt = createdAt,
        lastUsed = lastUsed,
        expiresAt = expiresAt
    )
}

internal fun AuthSession.toDTO(): AuthSessionDTO {
    return AuthSessionDTO(
        isActive = isActive,
        sessionUUID = sessionUUID,
        userId = userId,
        sessionName = sessionName,
        createdAt = createdAt,
        lastUsed = lastUsed,
        expiresAt = expiresAt
    )
}
