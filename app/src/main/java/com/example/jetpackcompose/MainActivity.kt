package com.example.jetpackcompose

import WeatherApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.data.repository.WeatherRepositoryImpl
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

@Preview(showBackground = true, name = "Weather App Preview")@Composable
fun PreviewWeatherApp() {
    // Create a mock or default WeatherViewModel for preview
    val viewModel = WeatherViewModel(WeatherRepositoryImpl())
    WeatherApp(viewModel)
}
