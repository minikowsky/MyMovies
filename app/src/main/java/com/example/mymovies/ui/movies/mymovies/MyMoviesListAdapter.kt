package com.example.mymovies.ui.movies.mymovies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.data.MovieDatabase
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.data.movie.MovieDao
import com.example.mymovies.data.usermovie.UserMovie

class MyMoviesListAdapter(val myMovies: LiveData<List<UserMovie>>,
                          val movies: LiveData<List<Movie>>,
                          private val onItemClicked: (UserMovie) -> Unit)
    : RecyclerView.Adapter<MyMoviesListAdapter.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val director: TextView = itemView.findViewById(R.id.movie_director)
        val cast: TextView = itemView.findViewById(R.id.movie_cast)
        val duration: TextView = itemView.findViewById(R.id.movie_duration)
        val rating: TextView = itemView.findViewById(R.id.movie_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_user_movie, parent, false) as View
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var movie: Movie? = null
        movies.value?.forEach {
            if(it.id == myMovies.value?.get(position)?.movieId ) {
            movie = it
            }
        }

        if(movie != null) {
            holder.title.text = movie?.title
            holder.director.text = movie?.director
            holder.cast.text = movie?.cast.toString()
            holder.duration.text = movie?.duration.toString() + " min"
            holder.rating.text = myMovies.value?.get(position)?.rating.toString() + "/5"
            holder.itemView.setOnClickListener{
                Log.println(Log.INFO,"INFO",myMovies.value?.get(position)!!.movieId.toString())
                onItemClicked(myMovies.value?.get(position)!!)
            }
        }


    }

    override fun getItemCount() = myMovies.value?.size?:0
}