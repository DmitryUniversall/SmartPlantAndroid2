package com.smartplant.data_auth.internal.remote

import com.smartplant.data_auth.internal.dto.requests.LoginRequestDTO
import com.smartplant.data_auth.internal.dto.requests.RefreshRequestDTO
import com.smartplant.data_auth.internal.dto.requests.RegisterRequestDTO
import com.smartplant.data_auth.internal.dto.responses.LoginResponseDTO
import com.smartplant.data_auth.internal.dto.responses.RefreshResponseDTO
import com.smartplant.data_auth.internal.dto.responses.RegisterResponseDTO

internal interface AuthRemoteDataSource {
    suspend fun login(dto: LoginRequestDTO): Result<LoginResponseDTO>
    suspend fun register(dto: RegisterRequestDTO): Result<RegisterResponseDTO>
    suspend fun refresh(dto: RefreshRequestDTO): Result<RefreshResponseDTO>
    suspend fun logout(): Result<Unit>
}
