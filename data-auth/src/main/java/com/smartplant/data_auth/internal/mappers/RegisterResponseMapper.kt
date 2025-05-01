package com.smartplant.data_auth.internal.mappers

import com.smartplant.core_mappers.toEntity
import com.smartplant.data_auth.internal.dto.responses.RegisterResponseDTO
import com.smartplant.domain_auth.entities.AuthInfo
import com.smartplant.domain_auth.entities.AuthResult
import com.smartplant.domain_auth.entities.AuthTokenPair

internal fun RegisterResponseDTO.toAuthResult(): AuthResult {
    return AuthResult(
        authInfo = AuthInfo(
            user = this.user.toEntity(),
            session = this.session.toEntity()
        ),
        tokenPair = AuthTokenPair(
            accessToken = this.accessToken,
            refreshToken = this.refreshToken
        )
    )
}
