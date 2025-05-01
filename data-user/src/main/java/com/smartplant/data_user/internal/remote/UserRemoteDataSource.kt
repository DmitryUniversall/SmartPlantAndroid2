package com.smartplant.data_user.internal.remote

import com.smartplant.data_user.internal.dto.responses.GetMeResponseDTO

internal interface UserRemoteDataSource {
    suspend fun getMe(): Result<GetMeResponseDTO>
}
