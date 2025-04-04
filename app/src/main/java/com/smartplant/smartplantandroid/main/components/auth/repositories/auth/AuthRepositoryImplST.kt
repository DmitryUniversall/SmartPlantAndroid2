package com.smartplant.smartplantandroid.main.components.auth.repositories.auth

import com.smartplant.smartplantandroid.main.components.auth.entities.AuthTokenPair
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginResponseDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RefreshRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterResponseDTO
import com.smartplant.smartplantandroid.main.network.apiCall
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod


class AuthRepositoryImplST : AuthRepository {
    companion object {
        @Volatile
        private var _instance: AuthRepositoryImplST? = null
        val instance: AuthRepositoryImplST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance() = _instance ?: synchronized(this) { _instance ?: AuthRepositoryImplST().also { _instance = it } }
    }

    override suspend fun login(dto: LoginRequestDTO): Result<LoginResponseDTO> {
        val request = HttpRequestBuilder().apply {
            url("auth/login")
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
            url("auth/register")
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

    override suspend fun refresh(dto: RefreshRequestDTO): Result<AuthTokenPair> {
        val request = HttpRequestBuilder().apply {
            url("auth/refresh")
            method = HttpMethod.Post
            setBody(dto)
        }

        try {
            val result = apiCall<AuthTokenPair>(request)
            return Result.success(result.apiResponse.data!!)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun logout(): Result<Unit> {
        val request = HttpRequestBuilder().apply {
            url("auth/logout")
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
