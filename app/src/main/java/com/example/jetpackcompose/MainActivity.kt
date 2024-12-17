package com.example.jetpackcompose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.data.WeatherRepositoryImpl
import com.example.jetpackcompose.model.WeatherViewModel
import com.example.jetpackcompose.model.WeatherViewModelFactory
import com.example.jetpackcompose.service.PopupService
import com.example.jetpackcompose.ui.WeatherApp

class MainActivity : ComponentActivity() {
    // Request permission result launcher
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, start the service
                startPopupService()
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission denied, notifications won't work", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the device is running Android 13 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Request the notification permission
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // If the device is running a lower version, no need for the permission
            startPopupService()
        }

        // Set up UI
        setContent {
            val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(
                WeatherRepositoryImpl())
            )
            WeatherApp(viewModel)
        }
    }

    // Function to start the PopupService
    private fun startPopupService() {
        val serviceIntent = Intent(applicationContext, PopupService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Start service as foreground
            startForegroundService(serviceIntent)
        } else {
            // Start service normally for older versions
            startService(serviceIntent)
        }
    }
}
