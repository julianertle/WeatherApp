package com.example.jetpackcompose.data

import com.example.jetpackcompose.api.WeatherApiService
import com.google.gson.Gson

interface WeatherRepository {
    suspend fun getCurrentWeather(): WeatherData
    suspend fun getWeatherForecast(): List<WeatherData>

    suspend fun getCurrentRawWeather(city: String): String {
        val rawJson = WeatherApiService.fetchRawWeather(city)
        return ((if (rawJson != null) {
            Gson().fromJson(rawJson, WeatherData::class.java)
        } else {
            //WeatherData() // Or return null if that's preferable
        }) as WeatherData).toString()
    }
}
