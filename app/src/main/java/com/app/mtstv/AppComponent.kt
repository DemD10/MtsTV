package com.app.mtstv

fun app() = AppComponent.instance

interface AppComponent {

    val viewModelFactory: ViewModelFactory

    companion object {
        lateinit var instance: AppComponent
    }
}