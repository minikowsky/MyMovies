package com.example.mymovies.data.usermovie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.data.movie.Movie

@Dao
interface UserMovieDao {

    @Query("SELECT * FROM usermovies WHERE userEmail = :email")
    fun getAllMoviesByUser(email: String): LiveData<List<UserMovie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(userMovie: UserMovie)
}