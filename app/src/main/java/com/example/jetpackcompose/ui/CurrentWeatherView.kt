package com.example.jetpackcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        // Root column with background and rounded corners
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFE3F2FD), RoundedCornerShape(32.dp)) // Light gray background with rounded corners
                .padding(16.dp) // Padding inside the background
        ) {
            // Row to display city name and country
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "${it.name}, ${it.sys.country}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 30.sp),
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                iconUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Weather icon",
                        modifier = Modifier.size(120.dp)
                    )
                }
            }

           // Spacer(modifier = Modifier.height(100.dp))

            // Function to create rows for labels and values
            @Composable
            fun createWeatherInfoRow(label: String, value: String) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .padding(start = 32.dp)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 22.sp),
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(start = 45.dp)
                    ) {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 22.sp),
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            createWeatherInfoRow("Temp.:", "${it.main.temp}°C")
            Spacer(modifier = Modifier.height(8.dp))
            createWeatherInfoRow("Feels Like:", "${it.main.feels_like}°C")
            Spacer(modifier = Modifier.height(8.dp))
            createWeatherInfoRow("Humidity:", "${it.main.humidity}%")
            Spacer(modifier = Modifier.height(8.dp))
            createWeatherInfoRow("Wind:", "${it.wind.speed} m/s")
            Spacer(modifier = Modifier.height(8.dp))

            val sunriseTime = convertUnixToTime(it.sys.sunrise)
            val sunsetTime = convertUnixToTime(it.sys.sunset)

            createWeatherInfoRow("Sunrise:", sunriseTime)
            Spacer(modifier = Modifier.height(4.dp))
            createWeatherInfoRow("Sunset:", sunsetTime)

        }

    } ?: Text(
        text = "No current weather data available.",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Gray,
        modifier = Modifier.padding(16.dp)
    )
}

fun convertUnixToTime(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}
