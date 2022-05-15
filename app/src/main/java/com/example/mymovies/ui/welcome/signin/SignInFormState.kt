package com.example.mymovies.ui.welcome.signin

data class SignInFormState (
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)