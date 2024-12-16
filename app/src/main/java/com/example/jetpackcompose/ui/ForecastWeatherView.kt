package com.example.jetpackcompose.ui

import SearchBarSample
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.ui.components.WeatherCard
import com.example.jetpackcompose.data.Keys
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.model.WeatherViewModel
import kotlinx.coroutines.flow.map

@Composable
fun ForecastWeatherView(forecast: List<ForecastItem>) {
    val context = LocalContext.current
    var hometown by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    val weatherViewModel: WeatherViewModel = viewModel() // This creates the view model instance


    // Retrieve hometown from DataStore
    LaunchedEffect(Unit) {
        context.dataStore.data.map { preferences ->
            preferences[Keys.HOMETOWN_KEY] ?: ""
        }.collect { savedHometown ->
            hometown = savedHometown
        }
    }

    // Retrieve hometown and apiKey from DataStore when the view is opened
    LaunchedEffect(Unit) {
        // Collect both hometown and apiKey
        context.dataStore.data.collect { preferences ->
            // Retrieve values from DataStore
            hometown = preferences[Keys.HOMETOWN_KEY] ?: ""
            apiKey = preferences[Keys.API_TOKEN_KEY] ?: ""  // Ensure you have a key in the DataStore for API_KEY

            // Automatically fetch weather for hometown if it exists
            if (hometown.isNotEmpty()) {
                weatherViewModel.fetchWeatherData(hometown, apiKey)
            }
        }
    }

    val searchQuery = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Pass hometown as the initial query to the search bar
        SearchBarSample(
            selectedMenu = "Forecast",
            apiKey = apiKey,
            onQueryChanged = { query ->
                searchQuery.value = query // Update the search query when the input changes
                if (query.isNotEmpty()) {
                    weatherViewModel.fetchWeatherData(query,apiKey)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Display message if hometown is empty
        if (hometown.isEmpty()) {
            Text(
                text = "Set your hometown in settings",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                modifier = Modifier.padding(16.dp),
                color = Color.Gray,
                )
        } else {
            // Display forecast if hometown is set
            Text(
                text = "Forecast for $hometown",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(forecast.size) { index ->
                WeatherCard(forecastItem = forecast[index])
            }
        }
    }
}
