package com.example.jetpackcompose.ui

import SearchBarSample
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items  // Make sure to import items
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState().value // Use 'currentWeather'
    val forecast = viewModel.forecast.collectAsState().value // Use 'forecast'
    val iconUrl = viewModel.iconUrl.collectAsState().value // Get the icon URL

    var selectedItem by remember { mutableStateOf(0) } // 0: Home, 1: Forecast, 2: Settings

    // Background colors for the system bars
    val upperHalfColor = Color.White
    val lowerHalfColor = Color(0xFF1E88E5) // Material Design Blue 500

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background
        Column(modifier = Modifier.fillMaxSize()) {
            // Upper half
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(upperHalfColor)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(lowerHalfColor)
            )
        }

        // Content and scrolling
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(upperHalfColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 0.dp), // Ensure no extra bottom padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Row containing the SearchBar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SearchBarSample() // The SearchBar expands here
                }

                Spacer(modifier = Modifier.height(16.dp)) // Add space between the search bar and weather display

                // Display current weather
                if (selectedItem == 0) {
                    currentWeather?.let {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Current weather display
                            //CurrentWeatherView(weather = it)

                            // Weather icon
                            iconUrl?.let {
                                Image(
                                    painter = rememberAsyncImagePainter(it), // Use the new function here
                                    contentDescription = "Weather icon",
                                    modifier = Modifier.size(64.dp), // Adjust size as needed
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Display forecast
                if (selectedItem == 1) {
                    Text(text = "Forecast", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Ensure forecast is a list and not null
                    forecast.let {
                        // Use LazyColumn for forecast items
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(it) { weather ->
                                ForecastWeatherView(weather = weather) // Pass WeatherData to the composable
                            }
                        }
                    }
                }

                // Settings view
                if (selectedItem == 2) {
                    SettingsView() // Display SettingsView when selectedItem is 2
                }
            }

            // Bottom navigation bar
            BottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                color = lowerHalfColor
            )
        }
    }
}
