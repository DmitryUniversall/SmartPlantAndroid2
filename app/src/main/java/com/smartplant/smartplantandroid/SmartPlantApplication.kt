package com.smartplant.smartplantandroid

import android.app.Application
import com.smartplant.smartplantandroid.main.components.auth.services.auth.AuthServiceImplST

class SmartPlantApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeUtils()
        initializeServices()
    }

    private fun initializeUtils() {}

    private fun initializeServices() {
        AuthServiceImplST.createInstance(this)
    }
}