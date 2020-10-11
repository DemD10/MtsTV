package com.app.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FilmNetwork(
    val id: String,
    val poster: String,
    val year: Int
)