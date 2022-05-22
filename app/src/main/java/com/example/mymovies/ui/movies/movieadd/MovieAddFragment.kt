package com.example.mymovies.ui.movies.movieadd

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import com.example.mymovies.data.movie.Movie
import com.example.mymovies.databinding.FragmentMovieAddBinding
import com.google.firebase.auth.FirebaseAuth

class MovieAddFragment : Fragment() {

    private var _binding: FragmentMovieAddBinding? = null
    private val binding get() = _binding!!
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var viewModel: MovieAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieAddBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(
            requireActivity(),
            MovieAddViewModelFactory((requireNotNull(this.activity).application))
            )[MovieAddViewModel::class.java]
        return binding.root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMovieDuration.minValue = 40
        binding.addMovieDuration.maxValue = 240

        val title = binding.addMovieTitle
        val director = binding.addMovieDirector
        val duration = binding.addMovieDuration
        val cast = binding.addMovieCast


        binding.addMovieButton.setOnClickListener {
            if(title.text != null && director.text != null) {
                viewModel.createMovie(
                    Movie(
                        0,
                        title.text.toString(),
                        director.text.toString(),
                        cast.text.toString(),
                        duration.value))

                Toast.makeText(context,"Movie has been added!", Toast.LENGTH_LONG)
                    .show()

                title.text.clear()
                director.text.clear()
                cast.text.clear()
                duration.value = duration.minValue
            }

        }
    }
}