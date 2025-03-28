package com.smartplant.smartplantandroid.main.components.auth.repositories.user.dto

import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import kotlinx.serialization.SerialName

data class GetMeResponseDTO(
    @SerialName("user")
    val user: UserPrivate
)
