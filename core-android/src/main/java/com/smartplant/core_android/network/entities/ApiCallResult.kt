package com.smartplant.core_android.network.entities

class ApiCallResult<T>(
    val httpResponse: io.ktor.client.statement.HttpResponse,
    val apiResponse: ApiResponse<T>,
)
