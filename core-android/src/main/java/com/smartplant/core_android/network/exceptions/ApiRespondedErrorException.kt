package com.smartplant.core_android.network.exceptions

import com.smartplant.core_android.network.entities.ApiCallResult
import io.ktor.client.statement.request

class ApiRespondedErrorException (
    val result: ApiCallResult<Any>,
    message: String? = null,
    cause: Throwable? = null
) : ApiCallException(message ?:"API call ${result.httpResponse.request.url} failed with status: ${result.httpResponse.status} (${result.apiResponse.applicationStatusCode}), message: ${result.apiResponse.message}", cause)
