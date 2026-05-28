package com.example.roombooking.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.roombooking.R
import com.example.roombooking.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        // Скрываем нижнюю навигацию на экранах добавления/редактирования
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideNav = destination.id in listOf(
                R.id.addEditEventFragment,
                R.id.addEditRoomFragment,
                R.id.eventDetailFragment,
                R.id.roomDetailFragment,
                R.id.filterFragment
            )
            binding.bottomNavigation.visibility =
                if (hideNav) android.view.View.GONE else android.view.View.VISIBLE
        }
    }
}
