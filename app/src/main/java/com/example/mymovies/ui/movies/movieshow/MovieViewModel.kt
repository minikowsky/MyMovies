package com.example.mymovies.ui.movies.movieshow

import android.app.Application
import androidx.lifecycle.*
import com.example.mymovies.data.MovieDatabase
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.data.movie.MovieDao
import com.example.mymovies.data.usermovie.UserMovie
import com.example.mymovies.data.usermovie.UserMovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application, movieId: Long): AndroidViewModel(application) {
    private val userMovieDao: UserMovieDao = MovieDatabase.getInstance(application).userMovieDao
    private val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao

    val movie: LiveData<Movie> = movieDao.getMovieById(movieId)

    fun createUserMovie(userMovie: UserMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            userMovieDao.create(userMovie)
        }
    }

}

class MovieViewModelFactory(private val application: Application, private val movieId: Long)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(application, movieId) as T
        }
        throw IllegalArgumentException("Unknown VieModel class")
    }
}