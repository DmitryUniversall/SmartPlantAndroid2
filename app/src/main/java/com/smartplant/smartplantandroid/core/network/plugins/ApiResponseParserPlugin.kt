package com.smartplant.smartplantandroid.core.network.plugins

import com.smartplant.smartplantandroid.core.global_entities.ApiResponse
import com.smartplant.smartplantandroid.core.network.ApiResponseParseException
import io.ktor.client.*
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

class ApiResponseParserPlugin private constructor() {
    companion object : HttpClientPlugin<Unit, ApiResponseParserPlugin> {
        val ApiResponseKey = AttributeKey<ApiResponse>("ApiResponse")

        override val key: AttributeKey<ApiResponseParserPlugin> = AttributeKey("ApiResponseParserPlugin")

        override fun prepare(block: Unit.() -> Unit) = ApiResponseParserPlugin()

        override fun install(plugin: ApiResponseParserPlugin, scope: HttpClient) {
            scope.receivePipeline.intercept(HttpReceivePipeline.After) { response ->
                val responseText = response.bodyAsText()

                try {
                    val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(responseText)
                    response.call.attributes.put(ApiResponseKey, apiResponse)
                } catch (e: Exception) {
                    throw ApiResponseParseException("Failed to deserialize ApiResponse. Received: $responseText", e)
                }

                proceedWith(response)
            }
        }
    }
}
