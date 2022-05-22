package com.example.mymovies.ui.movies.movieshow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.mymovies.R
import com.example.mymovies.data.usermovie.UserMovie
import com.example.mymovies.databinding.FragmentMovieBinding
import com.google.firebase.auth.FirebaseAuth

class MovieFragment : Fragment() {

    private val navigationArgs: MovieFragmentArgs by navArgs()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(
            requireActivity(),
            MovieViewModelFactory(
                (requireNotNull(this.activity).application),
                navigationArgs.movieId)
        )[MovieViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, Observer{
            Log.d("MOVIEEE",it.toString())
            Log.d("MOVIEEE",navigationArgs.stars.toString())

            binding.showMovieTitle.text = it.title
            binding.showMovieDirector.text = it.director
            binding.showMovieDuration.text = it.duration.toString()
            binding.showMovieCast.text = it.cast
            binding.showMovieSeen.isChecked = navigationArgs.isSeen
            binding.showMovieRatingBar.rating = navigationArgs.stars
        })


        binding.showMovieButtonSave.setOnClickListener {
            Log.d("CREATE USERMOVIE", binding.showMovieSeen.isChecked.toString() )
            Log.d("CREATE USERMOVIE", binding.showMovieRatingBar.rating.toString() )
            if(binding.showMovieSeen.isChecked && binding.showMovieRatingBar.rating > 0f) {
                viewModel.createUserMovie(
                    UserMovie(
                    0,
                        navigationArgs.movieId,
                        firebaseAuth.currentUser?.email.toString(),
                        binding.showMovieRatingBar.rating
                    ))
            }
        }
    }

}