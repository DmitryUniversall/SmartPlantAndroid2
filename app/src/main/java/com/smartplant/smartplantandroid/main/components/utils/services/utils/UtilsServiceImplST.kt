package com.smartplant.smartplantandroid.main.components.utils.services.utils

import com.smartplant.smartplantandroid.main.components.utils.repositories.utils.UtilsRepository
import com.smartplant.smartplantandroid.main.components.utils.repositories.utils.UtilsRepositoryImplST

class UtilsServiceImplST : UtilsService {
    companion object {
        @Volatile
        private var _instance: UtilsServiceImplST? = null
        val instance: UtilsServiceImplST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance() = _instance ?: synchronized(this) { _instance ?: UtilsServiceImplST().also { _instance = it } }
    }

    private val utilsRepository: UtilsRepository get() = UtilsRepositoryImplST.instance

    override suspend fun ping(): Result<Unit> = utilsRepository.ping()
}
