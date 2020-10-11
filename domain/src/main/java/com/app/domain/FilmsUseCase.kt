package com.app.domain

class FilmsUseCase(private val filmsRepository: FilmsRepository) {

    fun fetchFilms() = filmsRepository.getFilms()
}