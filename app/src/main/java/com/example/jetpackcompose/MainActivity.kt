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
import com.example.jetpackcompose.viewmodel.WeatherViewModel
import com.example.jetpackcompose.viewmodel.WeatherViewModelFactory
import com.example.jetpackcompose.service.PopupService
import com.example.jetpackcompose.ui.WeatherApp

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) startPopupService()
            else Toast.makeText(this, "Permission denied, notifications won't work", Toast.LENGTH_LONG).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            startPopupService()
        }

        setContent {
            val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(WeatherRepositoryImpl()))
            WeatherApp(viewModel)
        }
    }

    private fun startPopupService() {
        val serviceIntent = Intent(applicationContext, PopupService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }
}
