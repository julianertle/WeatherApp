package com.example.jetpackcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.ui.components.WeatherCard
import SearchBarSample
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ForecastWeatherView(forecast: List<ForecastItem>) {
    val searchQuery = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Fixed SearchBar
        SearchBarSample(selectedMenu = "Forecast", onQueryChanged = { query ->
            searchQuery.value = query  // Update the search query state
        })

        // Display the entered query below the search bar
        if (searchQuery.value.isNotEmpty()) {
            Text(
                text = "5 days weather forecast for ${searchQuery.value}:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)  // Center the text horizontally
            )
            Spacer(modifier = Modifier.height(16.dp)) // Adjust height as needed
        }


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
