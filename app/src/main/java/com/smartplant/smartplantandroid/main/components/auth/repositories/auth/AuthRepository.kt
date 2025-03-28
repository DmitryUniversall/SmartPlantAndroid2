package com.smartplant.smartplantandroid.main.components.auth.repositories.auth

import com.smartplant.smartplantandroid.main.components.auth.entities.AuthTokenPair
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginResponseDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RefreshRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterRequestDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterResponseDTO


interface AuthRepository {
    suspend fun login(dto: LoginRequestDTO): Result<LoginResponseDTO>
    suspend fun register(dto: RegisterRequestDTO): Result<RegisterResponseDTO>
    suspend fun refresh(dto: RefreshRequestDTO): Result<AuthTokenPair>
    suspend fun logout(): Result<Unit>
}
