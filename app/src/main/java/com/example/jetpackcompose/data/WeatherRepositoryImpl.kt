package com.example.jetpackcompose.data

import com.example.jetpackcompose.api.WeatherApiService

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(): WeatherData {
        // Mock data for simplicity
        return WeatherData(
            temperature = 22.5,
            condition = "Sunny",
            iconUrl = "https://example.com/sunny.png",
            date = "Today"
        )
    }

    override suspend fun getWeatherForecast(): List<WeatherData> {
        // Mock forecast data
        return listOf(
            WeatherData(20.0, "Cloudy", "https://example.com/cloudy.png", "Tomorrow"),
            WeatherData(18.5, "Rainy", "https://example.com/rainy.png", "Day After Tomorrow")
        )
    }

    override suspend fun getWeather(city: String): WeatherData {
        return WeatherApiService.fetchWeather(city)
    }
}