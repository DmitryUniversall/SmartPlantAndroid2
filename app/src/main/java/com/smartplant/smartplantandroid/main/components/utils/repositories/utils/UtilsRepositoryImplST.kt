package com.smartplant.smartplantandroid.main.components.utils.repositories.utils

import com.smartplant.smartplantandroid.main.network.apiCall
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpMethod


class UtilsRepositoryImplST : UtilsRepository {
    companion object {
        @Volatile
        private var _instance: UtilsRepositoryImplST? = null
        val instance: UtilsRepositoryImplST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance() = _instance ?: synchronized(this) { _instance ?: UtilsRepositoryImplST().also { _instance = it } }
    }

    override suspend fun ping(): Result<Unit> {
        val request = HttpRequestBuilder().apply {
            url("utils/ping")
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