package com.example.mymovies.data.usermovie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mymovies.data.movie.Movie

@Entity(tableName = "usermovies",
        foreignKeys = [
            ForeignKey(
                entity = Movie::class,
                parentColumns = arrayOf("movieId"),
                childColumns = arrayOf("movieId"))
        ])
data class UserMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "movieId")
    val movieId: Long,
    val userEmail: String,
    val rating: Float
)
