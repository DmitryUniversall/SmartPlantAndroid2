package com.smartplant.smartplantandroid.main.components.auth.repositories.user

import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import com.smartplant.smartplantandroid.main.components.auth.repositories.user.dto.GetMeResponseDTO
import com.smartplant.smartplantandroid.main.network.apiCall
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class UserRepositoryImplST : UserRepository {
    companion object {
        @Volatile
        private var _instance: UserRepositoryImplST? = null
        val instance: UserRepositoryImplST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance() = _instance ?: synchronized(this) { _instance ?: UserRepositoryImplST().also { _instance = it } }
    }

    override suspend fun getMe(): Result<UserPrivate> {
        val request = HttpRequestBuilder().apply {
            url("/users/user/me")
            method = HttpMethod.Get
        }

        try {
            val result = apiCall<GetMeResponseDTO>(request)
            return Result.success(result.apiResponse.data!!.user)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}
