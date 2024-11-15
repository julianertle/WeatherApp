package com.example.jetpackcompose.ui

import androidx.compose.animation.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.data.WeatherData
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun CurrentWeatherView(currentWeather: WeatherData?, iconUrl: String?) {
    currentWeather?.let {
        Row(
            horizontalArrangement = Arrangement.Center, // Center the items in the Row
            verticalAlignment = Alignment.CenterVertically, // Vertically align items in the center
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center text horizontally
                modifier = Modifier.padding(end = 16.dp) // Add some space between the text and the icon
            ) {
                Text(
                    text = "${it.name}, ${it.sys.country}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 30.sp),

                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            // Display weather icon with larger size
            iconUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(120.dp) // Adjust size as needed
                )
            }
        }

        // Display other weather information below the city and icon
        Spacer(modifier = Modifier.height(16.dp))

        // Function to create rows for labels and values in two columns
        @Composable
        fun createWeatherInfoRow(label: String, value: String) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Label column (takes 50% of the width)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp) // Add some space between label and value
                        .padding(start = 32.dp) // Add some margin to the left side
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 22.sp),
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth() // Ensure label takes full width
                    )
                }
                /*Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp) // Add some space between label and value
                ) {
                    // Empty Column
                }*/
                // Value column (takes 50% of the width)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth() // Ensure value takes full width
                        .padding(start = 32.dp) // Add some margin to the left side
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 22.sp),
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth() // Ensure value takes full width

                    )
                }
            }
        }

        // Main temperature
        createWeatherInfoRow("Temperature:", "${it.main.temp}°C")

        Spacer(modifier = Modifier.height(8.dp))

        // Feels Like
        createWeatherInfoRow("Feels Like:", "${it.main.feels_like}°C")

        Spacer(modifier = Modifier.height(8.dp))

        // Humidity
        createWeatherInfoRow("Humidity:", "${it.main.humidity}%")

        Spacer(modifier = Modifier.height(8.dp))

        // Wind Speed
        createWeatherInfoRow("Wind:", "${it.wind.speed} m/s")

        Spacer(modifier = Modifier.height(8.dp))

        // Sunrise and Sunset
        val sunriseTime = convertUnixToTime(it.sys.sunrise)
        val sunsetTime = convertUnixToTime(it.sys.sunset)

        createWeatherInfoRow("Sunrise:", sunriseTime)

        Spacer(modifier = Modifier.height(4.dp))

        createWeatherInfoRow("Sunset:", sunsetTime)

    } ?: Text(
        text = "No current weather data available.",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Gray,
        modifier = Modifier.padding(16.dp)
    )
}

// Function to convert Unix timestamp to time format (HH:mm)
fun convertUnixToTime(timestamp: Long): String {
    val date = Date(timestamp * 1000) // Convert seconds to milliseconds
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}
