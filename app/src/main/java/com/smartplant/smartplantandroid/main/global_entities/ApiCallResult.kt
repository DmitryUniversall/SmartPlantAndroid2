package com.smartplant.smartplantandroid.main.global_entities

import io.ktor.client.statement.HttpResponse

class ApiCallResult<T> (
    val httpResponse: HttpResponse,
    val apiResponse: ApiResponse<T>
)
