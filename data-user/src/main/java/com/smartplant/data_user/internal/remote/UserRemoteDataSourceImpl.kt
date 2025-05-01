package com.smartplant.data_user.internal.remote

import com.smartplant.core_android.network.apiCall
import com.smartplant.data_user.internal.dto.responses.GetMeResponseDTO
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor() : UserRemoteDataSource {
    override suspend fun getMe(): Result<GetMeResponseDTO> {
        val request = HttpRequestBuilder().apply {
            url("users/user/me")
            method = HttpMethod.Get
        }

        try {
            val result = apiCall<GetMeResponseDTO>(request)
            return Result.success(result.apiResponse.data!!)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}
