package com.example.jetpackcompose.data

import com.example.jetpackcompose.api.WeatherApiService
import com.google.gson.Gson

interface WeatherRepository {
    suspend fun getCurrentRawWeather(city: String): WeatherData?
}
