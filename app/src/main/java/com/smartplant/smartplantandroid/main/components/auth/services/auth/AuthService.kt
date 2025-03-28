package com.smartplant.smartplantandroid.main.components.auth.services.auth

import com.smartplant.smartplantandroid.main.components.auth.entities.AuthTokenPair
import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.LoginResponseDTO
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.dto.RegisterResponseDTO

interface AuthService {
    val isAuthenticated: Boolean
    var accessToken: String?
    var refreshToken: String?
    var user: UserPrivate?

    suspend fun login(username: String, sessionName: String, password: String): Result<LoginResponseDTO>
    suspend fun register(username: String, sessionName: String, password: String): Result<RegisterResponseDTO>
    suspend fun refresh(): Result<AuthTokenPair>
    suspend fun logout(): Result<Unit>
}
