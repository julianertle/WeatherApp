package com.example.jetpackcompose.data

interface WeatherRepository {
    suspend fun getCurrentRawWeather(city: String): WeatherData?
}
