// WeatherApi.kt
package com.example.jetpackcompose.api

import com.example.jetpackcompose.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    // Define a GET request to fetch weather data by city name
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherData
}
