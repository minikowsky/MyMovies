package com.example.mymovies.data.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movieId")
    val id: Long,
    val title: String,
    val director: String,
    val cast: String,
    val duration: Int
)