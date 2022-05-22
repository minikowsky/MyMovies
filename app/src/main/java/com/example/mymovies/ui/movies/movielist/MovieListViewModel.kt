package com.example.mymovies.ui.movies.movielist

import android.app.Application
import androidx.lifecycle.*
import com.example.mymovies.data.MovieDatabase
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.data.movie.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application) :AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao
    val movies: LiveData<List<Movie>> = movieDao.getAllMovies()
}

class MovieListViewModelFactory(private val application: Application)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown VieModel class")
    }
}