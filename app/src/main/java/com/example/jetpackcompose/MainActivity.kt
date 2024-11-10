package com.example.jetpackcompose

import WeatherApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.data.WeatherRepositoryImpl
import com.example.jetpackcompose.domain.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use WeatherViewModelFactory to create WeatherViewModel
            val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(
                WeatherRepositoryImpl()
            ))

            // Set the content to WeatherApp with the created viewModel
            WeatherApp(viewModel)
        }
    }
}
