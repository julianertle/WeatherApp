package com.example.jetpackcompose.data

import com.example.jetpackcompose.api.WeatherApiService

class WeatherRepositoryImpl : WeatherRepository {

    override suspend fun getCurrentRawWeather(city: String): WeatherData? {
        // Call the WeatherApiService to get the weather data
        val weatherData = WeatherApiService.fetchWeather(city)

        // Return the structured data directly if successful
        return weatherData // If null, it will return null
    }
}
