package com.example.mymovies.ui.welcome.signup

data class SignUpFormState (
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val password2Error: Int? = null,
    val isDataValid: Boolean = false
        )