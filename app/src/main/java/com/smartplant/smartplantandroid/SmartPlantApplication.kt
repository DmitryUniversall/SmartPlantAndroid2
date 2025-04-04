package com.smartplant.smartplantandroid

import android.app.Application
import com.smartplant.smartplantandroid.main.components.auth.repositories.auth.AuthRepositoryImplST
import com.smartplant.smartplantandroid.main.components.auth.repositories.user.UserRepositoryImplST
import com.smartplant.smartplantandroid.main.components.auth.services.auth.AuthServiceImplST
import com.smartplant.smartplantandroid.main.components.auth.services.user.UserServiceImplST
import com.smartplant.smartplantandroid.main.components.utils.repositories.utils.UtilsRepositoryImplST
import com.smartplant.smartplantandroid.main.components.utils.services.utils.UtilsServiceImplST

class SmartPlantApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeUtils()
        initializeServices()
    }

    private fun initializeUtils() {}

    private fun initializeServices() {
        UtilsRepositoryImplST.createInstance()
        UserRepositoryImplST.createInstance()
        AuthRepositoryImplST.createInstance()

        UtilsServiceImplST.createInstance()
        UserServiceImplST.createInstance()
        AuthServiceImplST.createInstance(this)
    }
}
