package com.smartplant.domain_auth.entities

data class AuthResult(
    val authInfo: AuthInfo,
    val tokenPair: AuthTokenPair,
)
