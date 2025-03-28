package com.smartplant.smartplantandroid.main.network.exceptions

class ApiCallFailedException(
    message: String? = null,
    cause: Throwable? = null
) : ApiCallException(message ?: "Failed to perform api call", cause)
