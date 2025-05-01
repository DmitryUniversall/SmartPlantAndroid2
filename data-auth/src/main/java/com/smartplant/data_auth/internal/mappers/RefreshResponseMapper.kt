package com.smartplant.data_auth.internal.mappers

import com.smartplant.data_auth.internal.dto.responses.RefreshResponseDTO
import com.smartplant.domain_auth.entities.AuthTokenPair

internal fun RefreshResponseDTO.toAuthTokenPair(): AuthTokenPair {
    return AuthTokenPair(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}
