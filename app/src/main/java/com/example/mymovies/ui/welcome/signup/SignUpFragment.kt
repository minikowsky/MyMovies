package com.example.mymovies.ui.welcome.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.databinding.FragmentSignupBinding
import com.example.mymovies.ui.login.afterTextChanged

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)

        signUpViewModel = ViewModelProvider(requireActivity(), SignUpViewModelFactory())[SignUpViewModel::class.java]

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.editTextEmail
        val password = binding.editTextPassword
        val password2 = binding.editTextPassword2
        val register = binding.buttonRegister

        signUpViewModel.signUpFormState.observe(viewLifecycleOwner, Observer{

            if(it.passwordError != null) {
                password.error = getString(it.passwordError)
            } else if(it.password2Error != null) {
                password2.error = getString(it.password2Error)
            }

            if(it.emailError != null) {
                email.error = getString(it.emailError)
            }
        })

        signUpViewModel.isSignedUp.observe(viewLifecycleOwner, Observer{
            if(it) {
                //TODO: close activity and open new activity

            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        })

        password.apply {
            afterTextChanged {
                signUpViewModel.registerDataChanged(
                    email.text.toString(),
                    password.text.toString(),
                    password2.text.toString())
            }
        }

        email.apply {
            afterTextChanged {
                signUpViewModel.registerDataChanged(
                    email.text.toString(),
                    password.text.toString(),
                    password2.text.toString())
            }
        }

        register.setOnClickListener {
            if(signUpViewModel.signUpFormState.value?.isDataValid!!){
                signUpViewModel.register(email.text.toString(), password.text.toString())
            } else {

                Toast.makeText(requireContext(), "Register error!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }}