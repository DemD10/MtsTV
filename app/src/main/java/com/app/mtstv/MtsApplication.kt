package com.app.mtstv

import android.app.Application
import com.app.data.ApiModule

class MtsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val appModule = AppModule(this)
        AppComponent.instance = appModule
    }
}