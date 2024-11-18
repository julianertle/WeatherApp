package com.example.jetpackcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.ui.components.WeatherCard
import SearchBarSample
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ForecastWeatherView(forecast: List<ForecastItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Fixed SearchBar
        SearchBarSample(selectedMenu = "Forecast")

        // Scrollable content below the SearchBar
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(forecast.size) { index ->
                WeatherCard(forecastItem = forecast[index])
            }
        }
    }
}
