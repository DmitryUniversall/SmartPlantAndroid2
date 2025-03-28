package com.smartplant.smartplantandroid.main.network

import com.smartplant.smartplantandroid.main.components.auth.services.auth.AuthServiceImplST
import com.smartplant.smartplantandroid.main.global_entities.ApiCallResult
import com.smartplant.smartplantandroid.main.global_entities.ApiResponse
import com.smartplant.smartplantandroid.main.network.client.HttpClientProvider
import com.smartplant.smartplantandroid.main.network.exceptions.ApiRespondedErrorException
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val refreshMutex = Mutex()

suspend fun refreshToken() {
    // TODO: refresh
}

suspend inline fun <reified T> sendRequest(requestBuilder: HttpRequestBuilder): ApiCallResult<T> {
    val response: HttpResponse = HttpClientProvider.client.request { takeFrom(requestBuilder) }
    val apiResponse: ApiResponse<T> = response.body()
    return ApiCallResult(response, apiResponse)
}

suspend inline fun <reified T> apiCall(requestBuilder: HttpRequestBuilder): ApiCallResult<T> {
    if (AuthServiceImplST.instance.isAuthenticated) {
        requestBuilder.apply { header("Authorization", AuthServiceImplST.instance.accessToken) }
    }

    val callResult = sendRequest<T>(requestBuilder)

    if (callResult.apiResponse.applicationStatusCode in 3005..3015) {
        refreshMutex.withLock { refreshToken() }
        return sendRequest(requestBuilder)
    }

    if (callResult.apiResponse.applicationStatusCode !in 1000..1999) {
        throw ApiRespondedErrorException(callResult as ApiCallResult<Any>, callResult.apiResponse.message, null)
    }

    return callResult
}
