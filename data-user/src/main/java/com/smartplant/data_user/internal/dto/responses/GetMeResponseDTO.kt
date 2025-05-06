package com.smartplant.data_user.internal.dto.responses

import com.smartplant.core_dto.UserPrivateDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetMeResponseDTO(
    @SerialName("user")
    val user: UserPrivateDTO,
)
