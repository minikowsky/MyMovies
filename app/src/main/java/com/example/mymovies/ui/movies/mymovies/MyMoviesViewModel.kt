package com.example.mymovies.ui.movies.mymovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.data.MovieDatabase
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.data.movie.MovieDao
import com.example.mymovies.data.usermovie.UserMovie
import com.example.mymovies.data.usermovie.UserMovieDao

class MyMoviesViewModel(application: Application,
                        userMail: String): AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao
    val movies: LiveData<List<Movie>> = movieDao.getAllMovies()

    private val userMovieDao: UserMovieDao = MovieDatabase.getInstance(application).userMovieDao
    val userMovies: LiveData<List<UserMovie>> = userMovieDao.getAllMoviesByUser(userMail)

}
class MyMoviesViewModelFactory(private val application: Application,
                                private val userMail: String)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyMoviesViewModel::class.java)) {
            return MyMoviesViewModel(application, userMail) as T
        }
        throw IllegalArgumentException("Unknown VieModel class")
    }
}