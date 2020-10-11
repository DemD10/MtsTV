package com.app.mtstv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.domain.FilmsUseCase

class ViewModelFactory(private val filmsUseCase: FilmsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(filmsUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}