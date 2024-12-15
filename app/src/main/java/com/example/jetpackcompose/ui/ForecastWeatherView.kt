package com.example.jetpackcompose.ui

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.map

@Composable
fun ForecastWeatherView(forecast: List<ForecastItem>) {
    val context = LocalContext.current
    var hometown by remember { mutableStateOf("") }

    // Retrieve hometown from DataStore
    LaunchedEffect(Unit) {
        context.dataStore.data.map { preferences ->
            preferences[Keys.HOMETOWN_KEY] ?: ""
        }.collect { savedHometown ->
            hometown = savedHometown
        }
    }

    val searchQuery = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Forecast for $hometown",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(forecast.size) { index ->
                WeatherCard(forecastItem = forecast[index])
            }
        }
    }
}
