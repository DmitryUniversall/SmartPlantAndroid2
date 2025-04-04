package com.smartplant.smartplantandroid.main.components.utils.repositories.utils

interface UtilsRepository {
    suspend fun ping(): Result<Unit>
}