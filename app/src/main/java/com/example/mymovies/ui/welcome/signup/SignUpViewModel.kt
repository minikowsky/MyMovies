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
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                _isSignedUp.value = if(task.isSuccessful) {
                    Log.d("SIGN UP","User $email just signed up!")
                    true
                } else {
                    Log.d("SIGN UP","Attempt $email to sign up was refused!")
                    Log.d("FirebaseAuth", "onComplete" + task.exception?.message)
                    false
                }
        }
    }

    fun registerDataChanged(email: String, password: String, password2: String) {
        if(!isUserNameValid(email)) {
            _signUpForm.value = SignUpFormState(emailError = R.string.invalid_email)
        } else if(!isPasswordValid(password)) {
            _signUpForm.value = SignUpFormState(passwordError = R.string.invalid_password)
        } else if(!isPassword2Valid(password, password2)) {
            _signUpForm.value = SignUpFormState(password2Error = R.string.different_passwords)
        } else {
            _signUpForm.value = SignUpFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    // A placeholder password2 validation check
    private fun isPassword2Valid(password: String, password2: String): Boolean {
        return password == password2
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