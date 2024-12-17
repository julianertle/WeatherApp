package com.example.jetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.data.WeatherRepositoryImpl
import com.example.jetpackcompose.model.WeatherViewModel
import com.example.jetpackcompose.model.WeatherViewModelFactory
import com.example.jetpackcompose.service.PopupService
import com.example.jetpackcompose.ui.WeatherApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start PopupService in the foreground
        val serviceIntent = Intent(applicationContext, PopupService::class.java) // Use applicationContext here
        startService(serviceIntent)

        // Load the UI
        setContent {
            val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(
                WeatherRepositoryImpl())
            )
            WeatherApp(viewModel)
        }
    }
}
