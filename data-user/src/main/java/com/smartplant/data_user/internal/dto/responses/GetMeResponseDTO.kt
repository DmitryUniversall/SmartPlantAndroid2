package com.smartplant.data_user.internal.dto.responses

import com.smartplant.domain_user.entities.UserPrivate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetMeResponseDTO(
    @SerialName("user")
    val user: UserPrivate,
)
