package com.example.jetpackcompose.ui

import SearchBarSample
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SearchBarSample()
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedItem == 0) {
                    CurrentWeatherView(
                        currentWeather = currentWeather,
                        iconUrl = iconUrl
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedItem == 1) {
                    ForecastWeatherView()
                }

                if (selectedItem == 2) {
                    SettingsView()
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
