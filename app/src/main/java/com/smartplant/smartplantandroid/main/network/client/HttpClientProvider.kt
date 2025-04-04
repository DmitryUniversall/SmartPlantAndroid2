package com.smartplant.smartplantandroid.main.network.client

import android.util.Log
import com.smartplant.smartplantandroid.BuildConfig
import com.smartplant.smartplantandroid.core.logs.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {
    val client: HttpClient by lazy {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KTOR_CLIENT", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            defaultRequest {
                contentType(ContentType.Application.Json)
                AppLogger.info(BuildConfig.API_URL)
                url(BuildConfig.API_URL)
            }
        }
    }
}
