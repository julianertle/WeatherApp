package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.model.WeatherData

interface WeatherRepository {
    suspend fun getCurrentWeather(): WeatherData
    suspend fun getWeatherForecast(): List<WeatherData>
}