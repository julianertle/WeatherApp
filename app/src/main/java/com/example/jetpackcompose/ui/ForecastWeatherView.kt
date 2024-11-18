package com.example.jetpackcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import SearchBarSample
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.data.ForecastItem

@Composable
fun ForecastWeatherView(forecast: List<ForecastItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Search bar
        SearchBarSample(selectedMenu = "Forecast")

        // Loop through the forecast list and display a WeatherCard for each item
        forecast.forEach { item ->
            WeatherCard(forecastItem = item)
        }
    }
}
