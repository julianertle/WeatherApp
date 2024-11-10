package com.example.jetpackcompose.ui

import android.app.Activity
import android.os.Build
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
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState().value
    val forecast = viewModel.forecast.collectAsState().value

    var selectedItem by remember { mutableStateOf(0) } // 0: Home, 1: Forecast, 2: Settings

    // Background for the android system bars (camera and bottom navigation)
    // Define color variables
    val backgroundColor = Color.Gray
    val lowerHalfColor = Color.Yellow

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
                    .background(backgroundColor)
            )

            // Lower half (Yellow background)
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
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                /*.fillMaxSize()*/
                .padding(bottom = 0.dp), // Make space for the BottomNavBar
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
        }

        BottomNavBar(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                /*.fillMaxWidth()*/
        )
    }
}
