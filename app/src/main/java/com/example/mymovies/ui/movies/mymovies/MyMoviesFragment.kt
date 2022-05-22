package com.example.mymovies.ui.movies.mymovies

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMyMoviesBinding
import com.google.firebase.auth.FirebaseAuth

class MyMoviesFragment : Fragment() {

    private var _binding: FragmentMyMoviesBinding? = null
    private val binding get() = _binding!!
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var viewModel: MyMoviesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyMoviesBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(
            requireActivity(),
            MyMoviesViewModelFactory(
                (requireNotNull(this.activity).application),
                firebaseAuth.currentUser?.email.toString())
        )[MyMoviesViewModel::class.java]

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myMoviesListAdapter = MyMoviesListAdapter(viewModel.userMovies, viewModel.movies) {
            val action = MyMoviesFragmentDirections
                .actionFragmentMyMoviesToMovieFragment(it.movieId, true, it.rating)
            this.findNavController().navigate(action)
        }

        viewModel.userMovies.observe(viewLifecycleOwner,
            Observer {
                if(it.isEmpty()) {
                    Toast.makeText(context,"You didn't watch anything!", Toast.LENGTH_LONG)
                        .show()
                }

                myMoviesListAdapter.notifyDataSetChanged()
            })

        viewModel.movies.observe(viewLifecycleOwner,
            Observer {
                myMoviesListAdapter.notifyDataSetChanged()
            })

        val myMoviesListLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewMyMovies.let {
            it.adapter = myMoviesListAdapter
            it.layoutManager = myMoviesListLayoutManager
        }
    }

}