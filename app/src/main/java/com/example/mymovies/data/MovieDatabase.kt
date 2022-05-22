package com.example.mymovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.data.movie.MovieDao
import com.example.mymovies.data.usermovie.UserMovie
import com.example.mymovies.data.usermovie.UserMovieDao

@Database(entities = [Movie::class, UserMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase(){

    abstract val movieDao: MovieDao
    abstract val userMovieDao: UserMovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context) : MovieDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}