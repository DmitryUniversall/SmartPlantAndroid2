package com.smartplant.api_utils.pub.utils

interface ApiUtils {
    suspend fun ping(): Result<Unit>
}
