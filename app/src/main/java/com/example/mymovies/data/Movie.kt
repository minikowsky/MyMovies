package com.example.mymovies.data

data class Movie (
    val title: String,
    val director: String,
    val cast: Cast? = null,
    val elapsedTime: Int? = null,
    val rating: Int? = null,
    val status: Status? = null
        )