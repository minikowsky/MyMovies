package com.example.mymovies.ui.welcome.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import com.example.mymovies.ui.welcome.signin.SignInFormState
import com.example.mymovies.ui.welcome.signin.SignInViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel(private val firebaseAuth: FirebaseAuth): ViewModel() {

    private val _signUpForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _signUpForm

    private val _isSignedUp = MutableLiveData<Boolean>()
    val isSignedUp : LiveData<Boolean> = _isSignedUp

    fun register(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                Log.d("SIGN IN","User $email just signed in!")
                _isSignedUp.value = true
            } else {
                Log.d("SIGN IN","Attempt $email to sign in was refused!")
                _isSignedUp.value = false
            }
        }

    }

    fun registerDataChanged(email: String, password: String, password2: String) {
        if(!isUserNameValid(email)) {
            _signUpForm.value = SignUpFormState(emailError = R.string.invalid_email)
        } else if(!isPasswordValid(password)) {
            _signUpForm.value = SignUpFormState(passwordError = R.string.invalid_password)
        } else if(!isPassword2Valid(password, password2)) {

        } else {
            _signUpForm.value = SignUpFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    // A placeholder password2 validation check
    private fun isPassword2Valid(password: String, password2: String): Boolean {
        return password.equals(password2)
    }
}

class SignUpViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(
                firebaseAuth = FirebaseAuth.getInstance()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}