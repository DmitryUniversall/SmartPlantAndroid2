package com.smartplant.core_mappers

import com.smartplant.core_dto.UserPrivateDTO
import com.smartplant.domain_user.entities.UserPrivate

fun UserPrivateDTO.toEntity(): UserPrivate {
    return UserPrivate(
        id = this.id,
        username = this.username,
        createdAt = this.createdAt
    )
}
