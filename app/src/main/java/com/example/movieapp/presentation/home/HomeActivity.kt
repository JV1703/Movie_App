package com.example.movieapp.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavBar()
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_bg)
    }

    private fun setupBottomNavBar() {
        val navHost = supportFragmentManager.findFragmentById(R.id.home_nav_host) as NavHostFragment
        navController = navHost.navController
        binding.homeBottomNav.setupWithNavController(navController)
    }

}