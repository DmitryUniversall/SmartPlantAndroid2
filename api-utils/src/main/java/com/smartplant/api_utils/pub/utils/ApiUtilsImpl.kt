package com.smartplant.api_utils.pub.utils

import com.smartplant.core_android.network.apiCall
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.client.request.url
import javax.inject.Inject

class ApiUtilsImpl @Inject constructor() : ApiUtils {
    override suspend fun ping(): Result<Unit> {
        val request = HttpRequestBuilder().apply {
            url("utils/ping/")
            method = HttpMethod.Get
        }

        try {
            apiCall<Unit>(request)
            return Result.success(Unit)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}
