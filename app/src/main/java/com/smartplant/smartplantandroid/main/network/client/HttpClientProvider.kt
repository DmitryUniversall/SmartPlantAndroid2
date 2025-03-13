package com.smartplant.smartplantandroid.main.network.client

import com.smartplant.smartplantandroid.core.network.plugins.ApiResponseParserPlugin
import com.smartplant.smartplantandroid.main.components.auth.services.AuthServiceST
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {
    val client: HttpClient by lazy {
        HttpClient(Android) {
            install(Logging) { level = LogLevel.BODY }

            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }

            install(ApiResponseParserPlugin)

            install(DefaultRequest) {
                if (AuthServiceST.instance.isAuthenticated()) header("Authorization", "Bearer ${AuthServiceST.instance.getAccessToken()}")
            }
        }
    }
}