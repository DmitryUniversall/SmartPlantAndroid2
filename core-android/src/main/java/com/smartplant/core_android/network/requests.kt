package com.smartplant.core_android.network

import com.smartplant.core_android.network.client.HttpClientProvider
import com.smartplant.core_android.network.entities.ApiCallResult
import com.smartplant.core_android.network.entities.ApiResponse
import com.smartplant.core_android.network.exceptions.ApiRespondedErrorException
import io.ktor.client.call.body
import io.ktor.client.request.request
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val refreshMutex = Mutex()

suspend fun refreshToken() {
    // TODO: refresh
}

suspend inline fun <reified T> sendRequest(requestBuilder: io.ktor.client.request.HttpRequestBuilder): ApiCallResult<T> {
    val response: io.ktor.client.statement.HttpResponse = HttpClientProvider.client.request { takeFrom(requestBuilder) }
    val apiResponse: ApiResponse<T> = response.body()
    return ApiCallResult(response, apiResponse)
}

suspend inline fun <reified T> apiCall(requestBuilder: io.ktor.client.request.HttpRequestBuilder): ApiCallResult<T> {  // TODO
//    if (AuthServiceImplST.instance.isAuthenticated) {
//        requestBuilder.apply { header("Authorization", AuthServiceImplST.instance.accessToken) }
//    }

    val callResult = sendRequest<T>(requestBuilder)

    if (callResult.apiResponse.applicationStatusCode != 3009 && callResult.apiResponse.applicationStatusCode in 3005..3015) {
        refreshMutex.withLock { refreshToken() }
        return sendRequest(requestBuilder)
    }

    if (callResult.apiResponse.applicationStatusCode !in 1000..1999) {
        @Suppress("UNCHECKED_CAST")
        throw ApiRespondedErrorException(callResult as ApiCallResult<Any>, callResult.apiResponse.message, null)
    }

    return callResult
}
