package com.example.jetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.jetpackcompose.model.WeatherViewModel
import com.example.jetpackcompose.ui.components.BottomNavBar

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val currentWeather by viewModel.currentWeather.collectAsState()
    val forecast by viewModel.forecast.collectAsState()
    val iconUrl by viewModel.iconUrl.collectAsState()

    var selectedItem by remember { mutableStateOf(0) }

    val upperHalfColor = Color.White
    val lowerHalfColor = Color(0xFF1E88E5)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(upperHalfColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (selectedItem == 0) {
                    CurrentWeatherView(
                        currentWeather = currentWeather,
                        iconUrl = iconUrl
                    )
                }

                if (selectedItem == 1) {
                    ForecastWeatherView(forecast = forecast) // Pass the viewModel here
                }

                if (selectedItem == 2) {
                    SettingsView(onSave = { selectedItem = 0 }) // Navigate back to Home (0 index)
                }
            }

            BottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
                modifier = Modifier.align(Alignment.BottomCenter),
                color = lowerHalfColor
            )
        }
    }
}
