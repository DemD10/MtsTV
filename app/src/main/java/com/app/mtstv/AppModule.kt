package com.app.mtstv

import android.content.Context
import com.app.data.ApiComponent
import com.app.data.api
import com.app.data.data
import com.app.domain.FilmsUseCase

class AppModule(
    context: Context
) : AppComponent {

    private val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val apiComponent = api(sharedPreferences)

    private val filmsRepository = data(apiComponent.filmsApi, apiComponent.appCache)

    private val filmsUseCase = FilmsUseCase(filmsRepository)

    override val viewModelFactory: ViewModelFactory = ViewModelFactory(
        filmsUseCase
    )
}