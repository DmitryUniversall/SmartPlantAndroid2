package com.smartplant.smartplantandroid.main.global_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("ok")
    val ok: Boolean,

    @SerialName("application_status_code")
    val applicationStatusCode: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: T? = null
)
