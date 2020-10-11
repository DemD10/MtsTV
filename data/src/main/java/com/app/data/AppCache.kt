package com.app.data

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class AppCache(private val sharedPreferences: SharedPreferences, moshi: Moshi) {

    companion object {
        private const val FILMS = "films"
    }

    private val type = Types.newParameterizedType(List::class.java, FilmNetwork::class.java)
    private val cachedFilmsAdapter by lazy { moshi.adapter<List<FilmNetwork>>(type) }

    fun isExists() = sharedPreferences.contains(FILMS)

    fun saveByCache(films: List<FilmNetwork>) {
        sharedPreferences.edit().apply {
            putString(FILMS, cachedFilmsAdapter.toJson(films))
            commit()
        }
    }

    fun getFromCache(): List<FilmNetwork> {
        return cachedFilmsAdapter.fromJson(sharedPreferences.getString(FILMS, null))!!
    }
}