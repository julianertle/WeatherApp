package com.example.jetpackcompose.data

import com.example.jetpackcompose.api.WeatherApiService

class WeatherRepositoryImpl : WeatherRepository {

    override suspend fun getCurrentRawWeather(city: String, apiKey: String): WeatherData? {
        return WeatherApiService.fetchWeather(city, apiKey)
    }
}
