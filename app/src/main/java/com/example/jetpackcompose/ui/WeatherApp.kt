package com.example.jetpackcompose.ui


import SearchBarSample
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.domain.WeatherViewModel
import androidx.compose.foundation.lazy.items  // Make sure to import items
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState().value
    val forecast = viewModel.forecast.collectAsState().value

    var selectedItem by remember { mutableStateOf(0) } // 0: Home, 1: Forecast, 2: Settings

    // Background for the android system bars (camera and bottom navigation)
    // Define color variables
    val upperHalfColor = Color.White
    val lowerHalfColor = Color(0xFF1E88E5) // Material Design Blue 500 (popular blue)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Upper half (Gray background)
            Box(
                modifier = Modifier
                    .weight(1f)  // Take half of the available space
                    .fillMaxWidth()
                    .background(upperHalfColor)
            )

            Box(
                modifier = Modifier
                    .weight(1f)  // Take the remaining half
                    .fillMaxWidth()
                    .background(lowerHalfColor)
            )
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(upperHalfColor)
    ) {
        Column(
            modifier = Modifier
                /*.fillMaxSize()*/
                .padding(bottom = 0.dp), // Make space for the BottomNavBar
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SearchBarSample()
            }

            Spacer(modifier = Modifier.height(16.dp)) // Add some space between the search bar and the weather display


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
            if (selectedItem == 2) {
                SettingsView()  // Display the SettingsView when selectedItem is 2
            }
        }

        BottomNavBar(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                /*.fillMaxWidth()*/,
            color = lowerHalfColor
        )
    }
}
