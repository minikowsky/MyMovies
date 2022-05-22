package com.example.mymovies.ui.welcome.signin

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel(private val firebaseAuth: FirebaseAuth): ViewModel() {

    private val _signInForm = MutableLiveData<SignInFormState>()
    val signInFromState: LiveData<SignInFormState> = _signInForm

    private val _isSignedIn = MutableLiveData<Boolean>()
    val isSignedIn : LiveData<Boolean> = _isSignedIn

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                Log.d("SIGN IN","User $email just signed in!")
                _isSignedIn.value = true
            } else {
                _isSignedIn.value = false
                Log.d("SIGN IN","Attempt $email to sign in was refused!")
                Log.d("FirebaseAuth", "onComplete" + task.exception?.message)
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if(!isUserNameValid(email)) {
            _signInForm.value = SignInFormState(emailError = R.string.invalid_email)
        } else if(!isPasswordValid(password)) {
            _signInForm.value = SignInFormState(passwordError = R.string.invalid_password)
        } else {
            _signInForm.value = SignInFormState(isDataValid = true)
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
}

class SignInViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(
                firebaseAuth = FirebaseAuth.getInstance()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}