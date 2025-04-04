package com.smartplant.smartplantandroid.main.components.utils.services.utils

interface UtilsService {
    suspend fun ping(): Result<Unit>
}
