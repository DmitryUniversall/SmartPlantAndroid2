package com.smartplant.data_user.internal.mappers

import com.smartplant.data_user.internal.dto.responses.GetMeResponseDTO
import com.smartplant.domain_user.entities.UserPrivate

internal fun GetMeResponseDTO.toUserPrivate(): UserPrivate {
    return this.user
}
