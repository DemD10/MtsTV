package com.app.mtstv

import android.content.Context
import com.squareup.moshi.Moshi

fun app() = AppComponent.instance

interface AppComponent {

    val viewModelFactory: ViewModelFactory

    companion object {
        lateinit var instance: AppComponent
    }
}