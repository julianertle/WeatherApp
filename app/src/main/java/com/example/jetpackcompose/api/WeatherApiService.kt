// WeatherApiService.kt
package com.example.jetpackcompose.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private const val API_KEY = "31c02750cfcd532431987a20153cde7c"

    // Create the Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create the WeatherApi service
    val api: WeatherApi = retrofit.create(WeatherApi::class.java)

    // Method to fetch weather data for a city
    suspend fun fetchWeather(city: String) = api.getWeatherByCity(city, API_KEY)
}
