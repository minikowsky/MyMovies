package com.example.mymovies.ui.movies.movielist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMoviesListBinding
import com.google.firebase.auth.FirebaseAuth

class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(
            requireActivity(),
            MovieListViewModelFactory((requireNotNull(this.activity).application))
        )[MovieListViewModel::class.java]
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesListAdapter = MovieListAdapter(viewModel.movies) {
            val action = MoviesListFragmentDirections
                .actionFragmentMoviesListToMovieFragment(it.id,false,0f)
            this.findNavController().navigate(action)
        }

        viewModel.movies.observe(viewLifecycleOwner,
        Observer {
            if(it.isEmpty()) {
                Toast.makeText(context,"There are no movies in a database!", Toast.LENGTH_LONG)
                    .show()
            }

            moviesListAdapter.notifyDataSetChanged()
        })

        val moviesListLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewMoviesList.let {
            it.adapter = moviesListAdapter
            it.layoutManager = moviesListLayoutManager
        }


        binding.fabAddMovie.setOnClickListener {
            it.findNavController().navigate(R.id.movieAddFragment)
        }
    }

}