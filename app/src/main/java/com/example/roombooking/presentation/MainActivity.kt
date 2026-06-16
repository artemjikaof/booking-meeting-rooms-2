package com.example.roombooking.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.roombooking.R
import com.example.roombooking.data.repository.YandexCalendarRepository
import com.example.roombooking.databinding.ActivityMainBinding
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch

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

        handleYandexAuthIntent(intent)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideNav = destination.id in listOf(
                R.id.addEditEventFragment,
                R.id.addEditRoomFragment,
                R.id.eventDetailFragment,
                R.id.roomDetailFragment,
                R.id.filterFragment
            )
            binding.bottomNavigation.visibility = if (hideNav) View.GONE else View.VISIBLE
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.let { handleYandexAuthIntent(it) }
    }

    private fun handleYandexAuthIntent(intent: Intent) {
        val data = intent.data ?: return
        if (data.scheme != "roombooking" || data.host != "yandex-auth") return

        val code = data.getQueryParameter("code")
        val error = data.getQueryParameter("error")

        when {
            error != null -> {
                Toast.makeText(this, "Ошибка авторизации Яндекс: $error", Toast.LENGTH_LONG).show()
            }
            code != null -> {
                lifecycleScope.launch {
                    val repository = EntryPointAccessors.fromApplication(
                        applicationContext,
                        MainActivityEntryPoint::class.java
                    ).yandexRepository()

                    val result = repository.handleAuthCode(code)

                    if (result.isSuccess) {
                        Toast.makeText(
                            this@MainActivity,
                            "Яндекс Календарь подключён ✓",
                            Toast.LENGTH_SHORT
                        ).show()
                        // ИСПРАВЛЕНО: navigateUp() + navigate() чтобы SettingsFragment
                        // пересоздался и onResume() вызвал refreshYandexStatus()
                        navController.navigate(R.id.settingsFragment)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка подключения: ${result.exceptionOrNull()?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface MainActivityEntryPoint {
        fun yandexRepository(): YandexCalendarRepository
    }
}