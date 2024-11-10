package com.example.jetpackcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.domain.WeatherViewModel
import androidx.compose.foundation.lazy.items  // Make sure to import items

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState().value
    val forecast = viewModel.forecast.collectAsState().value

    var selectedItem by remember { mutableStateOf(0) } // 0: Home, 1: Forecast, 2: Settings

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display current weather
        if (selectedItem == 0) {
            currentWeather?.let {
                CurrentWeatherView(weather = it)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display forecast
        if (selectedItem == 1) {
            Text(text = "Forecast", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            // Ensure forecast is a list and not null
            forecast.let {
                LazyColumn {
                    items(it) { weather ->  // 'it' is of type List<WeatherData>
                        ForecastWeatherView(weather = weather)  // Pass WeatherData to the composable
                    }
                }
            }
        }

        // Add the Bottom Navigation Bar
        Spacer(modifier = Modifier.weight(1f)) // Push the BottomNavigation to the bottom
        BottomNavBar(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it }
        )
    }
}
