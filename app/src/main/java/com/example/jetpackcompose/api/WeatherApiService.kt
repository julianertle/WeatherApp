package com.example.jetpackcompose.api

import android.util.Log
import com.example.jetpackcompose.data.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

object WeatherApiService {
    // Corrected BASE_URL to end with a slash
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"  // Ensure the base URL ends with "/"
    private const val API_KEY = "31c02750cfcd532431987a20153cde7c"
    private const val TAG = "WeatherService" // For logging

    // Create the Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Ensure baseUrl ends with a "/"
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

    private val client = OkHttpClient()

    // Function to fetch raw JSON weather data for a city
    suspend fun fetchRawWeather(city: String): String? {
        // Switch to the IO dispatcher for network operations
        return withContext(Dispatchers.IO) {
            val url = "$BASE_URL/weather?q=$city&appid=$API_KEY"  // Ensure the full URL is correct
            val request = Request.Builder()
                .url(url)
                .build()

            return@withContext try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val rawJson = response.body?.string()
                        Log.d(TAG, "Raw weather data for $city: $rawJson")
                        rawJson
                    } else {
                        Log.e(TAG, "Failed to fetch weather data: ${response.code}")
                        null
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching weather data for $city: ${e.message}")
                null
            }
        }
    }
}
