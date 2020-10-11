package com.app.domain

import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    fun getFilms(): Flow<List<FilmDomain>>
}