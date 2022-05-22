package com.example.mymovies.data.movie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.data.movie.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY movieId DESC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(movie: Movie)

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun getMovieById(movieId: Long): LiveData<Movie>
}