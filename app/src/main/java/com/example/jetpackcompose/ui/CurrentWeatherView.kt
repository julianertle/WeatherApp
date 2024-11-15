// CurrentWeatherView.kt
package com.example.jetpackcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.data.WeatherData

@Composable
fun CurrentWeatherView(currentWeather: WeatherData?, iconUrl: String?) {
    currentWeather?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display weather icon
            iconUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(64.dp) // Adjust size as needed
                )
            }

            // Display temperature or other weather data if needed
            Text(
                text = "Temperature: ${it.main.temp}°C", // Example of showing temp
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }
    } ?: Text(
        text = "No current weather data available.",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Gray,
        modifier = Modifier.padding(16.dp)
    )
}