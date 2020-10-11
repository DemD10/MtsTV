package com.app.mtstv

import android.content.Context
import com.app.data.ApiComponent
import com.app.data.ApiModule
import com.app.data.FilmsDataRepository
import com.app.domain.FilmsUseCase

class AppModule(
    context: Context
) : AppComponent {

    private val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val apiComponent = ApiModule(sharedPreferences)

    private val filmsRepository = FilmsDataRepository(apiComponent.filmsApi, apiComponent.appCache)

    private val filmsUseCase = FilmsUseCase(filmsRepository)

    override val viewModelFactory: ViewModelFactory = ViewModelFactory(
        filmsUseCase
    )
}