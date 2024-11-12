package com.example.jetpackcompose.data

import com.example.jetpackcompose.api.WeatherApiService
import com.google.gson.Gson

interface WeatherRepository {
    suspend fun getCurrentWeather(): WeatherData
    suspend fun getWeatherForecast(): List<WeatherData>

    suspend fun getCurrentRawWeather(city: String): String {
        val rawJson = WeatherApiService.fetchRawWeather(city)
        return ((if (rawJson != null) {
            // Parse raw JSON into WeatherData object using Gson
            Gson().fromJson(rawJson, WeatherData::class.java)
        } else {
            // Return an empty or default WeatherData object if the response is null
            //WeatherData() // Or return null if that's preferable
        }) as WeatherData).toString()
    }
    suspend fun getCurrentWeather(query: String): WeatherData {
        // Use the WeatherApiService to fetch weather data by city name
        val weatherData = WeatherApiService.fetchWeather(query)
        return weatherData ?: throw Exception("Weather data for $query not found") // Throw an exception if null
    }
}
