package com.smartplant.smartplantandroid.core.global_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiResponse(
    @SerialName("ok")
    val ok: Boolean,

    @SerialName("application_status_code")
    val applicationStatusCode: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: JsonElement? = null
)
