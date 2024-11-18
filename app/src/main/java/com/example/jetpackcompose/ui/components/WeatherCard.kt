package com.example.jetpackcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.ui.convertUnixToTime

@Composable
fun WeatherCard(forecastItem: ForecastItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp) // Increased padding for bigger cards
            .padding(horizontal = 16.dp), // Optional: added horizontal padding to the entire card
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Weather icon - increased size
        Image(
            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${forecastItem.weather.firstOrNull()?.icon}@2x.png"),
            contentDescription = null,
            modifier = Modifier.size(80.dp), // Increased size of the icon
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(24.dp)) // Increased spacing between icon and text

        // Weather data details - increased font size
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = convertUnixToTime(forecastItem.dt),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Increased font size
                modifier = Modifier.padding(bottom = 4.dp) // Added spacing between lines
            )
            Text(
                text = "${forecastItem.main.temp}°C - ${forecastItem.weather.firstOrNull()?.description ?: "N/A"}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp) // Increased font size
            )
        }
    }
}
