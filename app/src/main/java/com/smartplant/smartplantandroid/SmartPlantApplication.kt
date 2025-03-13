package com.smartplant.smartplantandroid

import android.app.Application

class SmartPlantApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeUtils()
        initializeServices()
    }

    private fun initializeUtils() {
        NetworkSettingsST.createInstance(this)
        DevicesSettingsST.createInstance(this)
        ApplicationStatusCodes.initialize(this)
        DevicesLocalDataManagerST.createInstance(this)
    }

    private fun initializeServices() {

    }
}