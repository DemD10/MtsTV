package com.app.mtstv

import android.app.Application

class MtsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val appModule = AppModule(this)
        AppComponent.instance = appModule
    }
}