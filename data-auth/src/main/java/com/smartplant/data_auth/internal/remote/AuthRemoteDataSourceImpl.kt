package com.smartplant.data_auth.internal.remote

import com.smartplant.core_android.network.apiCall
import com.smartplant.data_auth.internal.dto.requests.LoginRequestDTO
import com.smartplant.data_auth.internal.dto.requests.RefreshRequestDTO
import com.smartplant.data_auth.internal.dto.requests.RegisterRequestDTO
import com.smartplant.data_auth.internal.dto.responses.LoginResponseDTO
import com.smartplant.data_auth.internal.dto.responses.RefreshResponseDTO
import com.smartplant.data_auth.internal.dto.responses.RegisterResponseDTO
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor() : AuthRemoteDataSource {
    override suspend fun login(dto: LoginRequestDTO): Result<LoginResponseDTO> {
        val request = HttpRequestBuilder().apply {
            url("auth/login/")
            method = HttpMethod.Post
            setBody(dto)
        }

        try {
            val result = apiCall<LoginResponseDTO>(request)
            return Result.success(result.apiResponse.data!!)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun register(dto: RegisterRequestDTO): Result<RegisterResponseDTO> {
        val request = HttpRequestBuilder().apply {
            url("auth/register/")
            method = HttpMethod.Post
            setBody(dto)
        }

        try {
            val result = apiCall<RegisterResponseDTO>(request)
            return Result.success(result.apiResponse.data!!)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun refresh(dto: RefreshRequestDTO): Result<RefreshResponseDTO> {
        val request = HttpRequestBuilder().apply {
            url("auth/refresh/")
            method = HttpMethod.Post
            setBody(dto)
        }

        try {
            val result = apiCall<RefreshResponseDTO>(request)
            return Result.success(result.apiResponse.data!!)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun logout(): Result<Unit> {
        val request = HttpRequestBuilder().apply {
            url("auth/logout/")
            method = HttpMethod.Get
        }

        try {
            apiCall<RegisterResponseDTO>(request)
            return Result.success(Unit)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}
