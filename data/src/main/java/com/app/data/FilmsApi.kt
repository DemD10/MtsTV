package com.app.data

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

const val BASE_URL = "https://raw.githubusercontent.com/"

interface FilmsApi {
    @GET("ar2code/apitest/master/movies.json")
    fun getAccounts(): Flow<List<FilmNetwork>>
}