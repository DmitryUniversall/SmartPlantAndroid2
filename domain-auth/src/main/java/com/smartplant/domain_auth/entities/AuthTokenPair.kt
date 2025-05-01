package com.smartplant.domain_auth.entities

data class AuthTokenPair(
    val accessToken: String,
    val refreshToken: String,
)
