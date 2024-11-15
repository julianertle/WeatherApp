package com.example.jetpackcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.data.WeatherData

@Composable
fun ForecastWeatherView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Weather icon
        Image(
            painter = rememberAsyncImagePainter("weather.iconUrl"),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Weather data details
        Column {
            Text(text = "weather.date", style = MaterialTheme.typography.bodyLarge)
            Text(text = "${"weather.temperature"}°C - ${"weather.condition"}", color = Color.Gray)
        }
    }
}
