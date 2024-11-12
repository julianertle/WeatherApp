package com.example.jetpackcompose.api

import android.util.Log
import com.example.jetpackcompose.data.WeatherData
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

object WeatherApiService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "31c02750cfcd532431987a20153cde7c"
    private const val TAG = "WeatherApiService" // For logging

    // Create the Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create the WeatherApi service
    val api: WeatherApi = retrofit.create(WeatherApi::class.java)

    // Method to fetch weather data for a city
    suspend fun fetchWeather(city: String): WeatherData? {
        try {
            // Fetch the weather data using the API
            val response: Response<WeatherData> = api.getWeatherByCity(city, API_KEY)

            if (response.isSuccessful) {
                // Log the successful response body (the full JSON response)
                Log.d(TAG, "Weather data for $city: ${response.body()}")
                return response.body() // Return the body which contains the WeatherData
            } else {
                // Log the error body or the status code if not successful
                Log.e(TAG, "Failed to fetch weather data for $city. Error: ${response.code()}")
                return null
            }
        } catch (e: Exception) {
            // Handle error if fetching the data fails
            Log.e(TAG, "Error fetching weather data for $city: ${e.message}")
            throw e // Re-throw the exception after logging
        }
    }

    suspend fun fetchRawWeather(city: String): String? {
        return try {
            val response: Response<ResponseBody> = api.getRawWeatherByCity(city, API_KEY)
            if (response.isSuccessful) {
                val rawJson = response.body()?.string() // Extract raw JSON as string
                Log.d(TAG, "Raw weather data for $city: $rawJson")
                rawJson // Return the raw JSON string
            } else {
                Log.e(TAG, "Failed to fetch raw weather data: ${response.errorBody()?.string()}")
                null // Return null if the request was unsuccessful
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching raw weather data for $city: ${e.message}")
            null // Return null if an exception occurs
        }
    }


}
