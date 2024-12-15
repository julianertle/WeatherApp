package com.example.jetpackcompose.data

interface WeatherRepository {
    suspend fun getCurrentRawWeather(city: String, apiKey: String): WeatherData?
}
