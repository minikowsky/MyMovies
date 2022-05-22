package com.example.mymovies.ui.movies.movieadd

import android.app.Application
import androidx.lifecycle.*
import com.example.mymovies.data.MovieDatabase
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.data.movie.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieAddViewModel(application: Application): AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao


    fun createMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.create(movie)
        }
    }

}
class MovieAddViewModelFactory(private val application: Application)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieAddViewModel::class.java)) {
            return MovieAddViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown VieModel class")
    }
}