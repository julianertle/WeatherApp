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
fun CurrentWeatherView(weather: WeatherData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberAsyncImagePainter(weather.iconUrl),
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = "${weather.temperature}°C", style = MaterialTheme.typography.displayLarge)
        Text(text = weather.condition, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
    }
}
