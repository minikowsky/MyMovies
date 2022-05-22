package com.example.mymovies.ui.movies.movielist

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.data.movie.Movie

class MovieListAdapter(val movies: LiveData<List<Movie>>,
                       private val onItemClicked: (Movie) -> Unit)
    : RecyclerView.Adapter<MovieListAdapter.Holder>() {

        inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.movie_title)
            val director: TextView = itemView.findViewById(R.id.movie_director)
            val cast: TextView = itemView.findViewById(R.id.movie_cast)
            val duration: TextView = itemView.findViewById(R.id.movie_duration)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_movie, parent, false) as View
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = movies.value?.get(position)?.title
        holder.director.text = movies.value?.get(position)?.director
        holder.cast.text = movies.value?.get(position)?.cast.toString()
        holder.duration.text = movies.value?.get(position)?.duration.toString() + " min"
        holder.itemView.setOnClickListener{
            Log.println(Log.INFO,"INFO",movies.value?.get(position)!!.title)
            onItemClicked(movies.value?.get(position)!!)
        }
    }

    override fun getItemCount() = movies.value?.size?:0

}