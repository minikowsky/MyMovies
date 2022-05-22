package com.example.mymovies.ui.welcome.signin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.afterTextChanged
import com.example.mymovies.databinding.FragmentSigninBinding
import com.example.mymovies.ui.movies.MainActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninBinding.inflate(inflater, container, false)

        signInViewModel = ViewModelProvider(requireActivity(), SignInViewModelFactory())[SignInViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.editTextEmail
        val password = binding.editTextPassword
        val signIn = binding.buttonSignIn

        signInViewModel.signInFromState.observe(viewLifecycleOwner, Observer{

            if(it.passwordError != null) {
                password.error = getString(it.passwordError)
            }

            if(it.emailError != null) {
                email.error = getString(it.emailError)
            }
        })

        signInViewModel.isSignedIn.observe(viewLifecycleOwner, Observer {
            if(it != null && it == true){
                Log.d("LOG IN", it.toString())
                startMovieActivity()
            } else {
                Toast.makeText(requireContext(), "Invalid email or password",Toast.LENGTH_SHORT).show()
            }
        })

        password.apply {
            afterTextChanged {
                signInViewModel.loginDataChanged(email.text.toString(), password.text.toString())
            }
        }

        email.apply {
            afterTextChanged {
                signInViewModel.loginDataChanged(email.text.toString(), password.text.toString())
            }
        }

        signIn.setOnClickListener {
            signInViewModel.login(email.text.toString(), password.text.toString())
        }

    }

    private fun startMovieActivity() {
        val newActivity = Intent(requireContext(), MainActivity::class.java)
        startActivity(newActivity)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}