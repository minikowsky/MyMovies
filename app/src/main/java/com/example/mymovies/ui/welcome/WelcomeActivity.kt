package com.example.mymovies.ui.welcome

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mymovies.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView_welcome)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_welcome) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}